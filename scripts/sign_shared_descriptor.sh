export UNSIGNED_PSBT=""

# ALICE SIGNS
export ALICE_SIGNED_PSBT=$(bdk-cli wallet -w alice -d $ALICE_SIGNING_DESCRIPTOR sign --psbt $UNSIGNED_PSBT | jq -r ".psbt")

# BOB SIGNS
export ALICE_BOB_SIGNED_PSBT=$(bdk-cli wallet -w bob -d $BOB_SIGNING_DESCRIPTOR sign --psbt $ALICE_SIGNED_PSBT | jq -r ".psbt")