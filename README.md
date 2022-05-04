# karavan
Bitcoin multisig coordination software

## Start Spring Boot Server
```shell
./gradlew bootRun
```

## Run test scripts
1. Install rust cargo tool
   https://doc.rust-lang.org/cargo/getting-started/installation.html
2. Install the bdk-cli tool
   cargo install bdk-cli --features esplora-ureq,compiler
3. Install jq command
   brew install jq (for Mac)
   chocolatey install jq (for Windows)

4. Run the scripts
   cd scripts
   ./generate_test_private_keys.sh
   source ./create_test_descriptors.sh
   echo $SHARED_DESCRIPTOR
   echo $ALICE_SIGNING_DESCRIPTOR
   echo $BOB_SIGNING_DESCRIPTOR
   echo $CAROL_SIGNING_DESCRIPTOR
5. Use wallet with the shared descriptor
   bdk-cli wallet -d $SHARED_DESCRIPTOR sync
   bdk-cli wallet -d $SHARED_DESCRIPTOR get_balance
   bdk-cli wallet -d $SHARED_DESCRIPTOR get_new_address
   # Sent testnet coins to new address
   # wait for a confirmation
   bdk-cli wallet -d $SHARED_DESCRIPTOR sync
   bdk-cli wallet -d $SHARED_DESCRIPTOR get_balance

## Run Tests
```shell
./gradlew test
```

## Generate Kdoc for API documentation
```shell
./gradlew dokkaHtml
```
