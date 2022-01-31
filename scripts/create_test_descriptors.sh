#! /bin/bash

# Before using ths script you must run the generate_test_private_keys.sh script
# to set the created values in the parent shell use the command:
# source create_test_descriptors.sh

# Extract the XPRV values from the key json files
export ALICE_ROOT_XPRV=$(cat alice-key.json | jq -r '.xprv')
export BOB_ROOT_XPRV=$(cat bob-key.json | jq -r '.xprv')
export CAROL_ROOT_XPRV=$(cat carol-key.json | jq -r '.xprv')

# Derive the XPRV values for the BIP84 key path and the ROOT XPRV values
export ALICE_XPRV=$(bdk-cli key derive --xprv $ALICE_ROOT_XPRV --path "m/84'/1'/0'/0" | jq -r ".xprv")
export BOB_XPRV=$(bdk-cli key derive --xprv $BOB_ROOT_XPRV --path "m/84'/1'/0'/0" | jq -r ".xprv")
export CAROL_XPRV=$(bdk-cli key derive --xprv $CAROL_ROOT_XPRV --path "m/84'/1'/0'/0" | jq -r ".xprv")

# Derive the XPUB values for the BIP84 key path and the ROOT XPRV values
export ALICE_XPUB=$(bdk-cli key derive --xprv $ALICE_ROOT_XPRV --path "m/84'/1'/0'/0" | jq -r ".xpub")
export BOB_XPUB=$(bdk-cli key derive --xprv $BOB_ROOT_XPRV --path "m/84'/1'/0'/0" | jq -r ".xpub")
export CAROL_XPUB=$(bdk-cli key derive --xprv $CAROL_ROOT_XPRV --path "m/84'/1'/0'/0" | jq -r ".xpub")

export SHARED_DESCRIPTOR=$(bdk-cli compile "thresh(2,pk($ALICE_XPUB),pk($BOB_XPUB),pk($CAROL_XPUB))" -t "wsh" | jq -r ".descriptor")
#echo -e "SHARED_DESCRIPTOR=\"$SHARED_DESCRIPTOR\"\n"

export ALICE_SIGNING_DESCRIPTOR=$(bdk-cli compile "thresh(2,pk($ALICE_XPRV),pk($BOB_XPUB),pk($CAROL_XPUB))" -t "wsh" | jq -r ".descriptor")
#echo -e "ALICE_SIGNING_DESCRIPTOR=\"$ALICE_SIGNING_DESCRIPTOR\"\n"

export BOB_SIGNING_DESCRIPTOR=$(bdk-cli compile "thresh(2,pk($ALICE_XPUB),pk($BOB_XPRV),pk($CAROL_XPUB))" -t "wsh" | jq -r ".descriptor")
#echo -e "BOB_SIGNING_DESCRIPTOR=\"$BOB_SIGNING_DESCRIPTOR\"\n"

export CAROL_SIGNING_DESCRIPTOR=$(bdk-cli compile "thresh(2,pk($ALICE_XPUB),pk($BOB_XPUB),pk($CAROL_XPRV))" -t "wsh" | jq -r ".descriptor")
#echo -e "CAROL_SIGNING_DESCRIPTOR=\"$CAROL_SIGNING_DESCRIPTOR\"\n"
echo done!