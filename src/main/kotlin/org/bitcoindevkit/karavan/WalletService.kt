package org.bitcoindevkit.karavan

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import org.springframework.stereotype.Service
import com.fasterxml.jackson.module.kotlin.*
import org.bitcoindevkit.*


@Service
class WalletService {

    private val db = DatabaseConfig.Memory("")
    // Connecting to Electrum network
    private val electrumURL = "ssl://electrum.blockstream.info:60002"
    private val client =
        BlockchainConfig.Electrum(
            ElectrumConfig(electrumURL, null, 5u, null, 10u)
        )

    private lateinit var encryptedPSBT : String

    // Create null object of type BdkProgress
    // update() function is changed to do nothing
    private object NullProgress : BdkProgress {
        override fun update(progress: Float, message: String?) {}
    }

    private val transactionCompByHeight =  Comparator<Transaction> { a, b ->
        val aHeight = when(a) {
            is Transaction.Confirmed -> a.confirmation.height
            else -> null
        }
        val bHeight = when(b) {
            is Transaction.Confirmed -> b.confirmation.height
            else -> null
        }
        compareValues(aHeight,bHeight)
    }


    // Connect to Electrum network, sync wallet, and return balance as JSON
    fun getBalance(descriptor: String, networkIn: String): String{

        val network : Network
        val balance : ULong

        // Check if valid network
        if (networkIn.equals("TESTNET", ignoreCase = true))
            network = Network.TESTNET
        else
            return "Invalid Network: $networkIn!"

        val wallet = Wallet(descriptor, null, network, db, client)

        // sync balance of descriptor
        wallet.sync(progressUpdate = NullProgress, maxAddressParam = null)
        // get the balance
        balance = wallet.getBalance()

        // put balance into JSON and return it
        val mapper = ObjectMapper()
        val balanceJSON: ObjectNode = mapper.createObjectNode()
        balanceJSON.put("balance", balance.toString())
        val balanceJSONString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(balanceJSON)

        return balanceJSONString
    }

    // Connect to Electrum network, sync wallet, and return new address in string.
    fun getNewAddress(descriptor: String, networkIn: String): String{

        val network : Network
        val newAddress : String

        // Check if valid network
        if (networkIn.equals("TESTNET", ignoreCase = true))
            network = Network.TESTNET
        else
            return "Invalid Network: $networkIn!"

        val wallet = Wallet(descriptor, null, network, db, client)

        // sync balance of descriptor
        wallet.sync(progressUpdate = NullProgress, maxAddressParam = null)
        // get a new address
        newAddress = wallet.getNewAddress()

        return newAddress
    }

    // Return list of transactions in JSON format
    fun getTransactions(descriptor: String, networkIn: String): String {

        val network: Network

        // Check if valid network
        if (networkIn.equals("TESTNET", ignoreCase = true))
            network = Network.TESTNET
        else
            return "Invalid Network: $networkIn!"

        val wallet = Wallet(descriptor, null, network, db, client)

        // sync balance of descriptor
        wallet.sync(progressUpdate = NullProgress, maxAddressParam = null)
        // get transactions
        val transactionList = wallet.getTransactions()
        val transactionSorted = transactionList.sortedWith(transactionCompByHeight)

        // map transactionList of Transaction objects into JSON using Jackson
        val mapper = jacksonObjectMapper()
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(transactionSorted)
    }

    fun createUnsignedPSBT(descriptor: String, networkIn: String, recipient: String, amount: ULong, feeRate: Float?) : String {

        val network: Network
        // Check if valid network
        if (networkIn.equals("TESTNET", ignoreCase = true))
            network = Network.TESTNET
        else
            return "Invalid Network: $networkIn!"


        val wallet = Wallet(descriptor, null, network, db, client)
        wallet.sync(progressUpdate = NullProgress, maxAddressParam = null)
        val psbt = PartiallySignedBitcoinTransaction(wallet, recipient, amount, feeRate)
        val psbtSerialized = psbt.serialize()
        encryptedPSBT = psbtSerialized
        return psbtSerialized
    }

    fun broadcastSignedPSBT(descriptor: String, networkIn: String, psbtSerialized: String) : String {

        val network: Network
        // Check if valid network
        if (networkIn.equals("TESTNET", ignoreCase = true))
            network = Network.TESTNET
        else
            return "Invalid Network: $networkIn!"

        val wallet = Wallet(descriptor, null, network, db, client)
        //val psbt = PartiallySignedBitcoinTransaction.deserialize(psbtSerialized)
        //wallet.sync(progressUpdate = NullProgress, maxAddressParam = null)
        println("Encrypted PSBT: \n$encryptedPSBT")
        val psbt = PartiallySignedBitcoinTransaction.deserialize(encryptedPSBT)

        wallet.sign(psbt)

        val transaction = wallet.broadcast(psbt)

        // map transaction of Transaction type into JSON using Jackson and return it
        val mapper = jacksonObjectMapper()
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(transaction)
    }
}