# karavan
Bitcoin multisig coordination software

## Start Spring Boot Server

```shell
./gradlew bootRun
```

## Run API request to open wallet
```shell
curl -j -b /tmp/cookie-jar.txt -c /tmp/cookie-jar.txt -X PUT --location "http://localhost:8080/wallet" \
    -H "Content-Type: application/json" \
    -d "{
          \"network\": \"testnet\",
          \"descriptor\": \"wpkh([1f44db3b/84'/1'/0'/0]tpubDEtS2joSaGheeVGuopWunPzqi7D3BJ9kooggvasZWUzSVziMNKkrdfS7VnLDe6M4Cg6bw3j5oxRB5U7GMJGcFnDia6yUaFAdwWqyJQjn4Qp/0/*)\"
        }"
```

## Run API request to get balance of wallet
```shell
curl -b /tmp/cookie-jar.txt -X GET --location "http://localhost:8080/wallet/balance"
```

## Run API request to close wallet
```shell
curl -b /tmp/cookie-jar.txt -c /tmp/cookie-jar.txt -X DELETE --location "http://localhost:8080/wallet"
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
