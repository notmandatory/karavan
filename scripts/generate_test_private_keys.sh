#! /bin/bash

# Before using ths script you must install the bdk-cli tool with the command:
# cargo install bdk-cli --features esplora-ureq,compiler

# Generate three sets of mnemonic seed words and corresponding private keys
bdk-cli key generate > alice-key.json
bdk-cli key generate > bob-key.json
bdk-cli key generate > carol-key.json
echo done!