package org.bitcoindevkit.karavan

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import org.springframework.stereotype.Service
import com.fasterxml.jackson.module.kotlin.*
import org.bitcoindevkit.*
import java.util.*
import kotlin.Comparator

/**
 * Service class for the SpringBoot Project
 */
@Service
class WalletService {

    // Configuration for Electrum Network
    private val db = DatabaseConfig.Memory
    private val electrumURL = "ssl://electrum.blockstream.info:60002"
    private val client =
        BlockchainConfig.Electrum(
            ElectrumConfig(electrumURL, null, 5u, null, 10u)
        )

    // Create null object of type BdkProgress
    // update() function is changed to do nothing
    private object NullProgress : BdkProgress {
        override fun update(progress: Float, message: String?) {}
    }

    // Comparator for type Transaction, sorting by block height
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

    /**
     * Returns wallet balance into JSON object
     * @see WalletController.getBalance
     */
    fun getBalance(descriptor: String, networkIn: String): String{

        val network : Network = Network.valueOf(networkIn)
        val balance : ULong

        // Set up bdk::wallet object
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

    /**
     * Returns new address for wallet
     * @see WalletController.getNewAddress
     */
    fun getNewAddress(descriptor: String, networkIn: String): String{

        val network : Network = Network.valueOf(networkIn)

        // Set up bdk::wallet object
        val wallet = Wallet(descriptor, null, network, db, client)

        // sync balance of descriptor
        wallet.sync(progressUpdate = NullProgress, maxAddressParam = null)

        // get a new address
        return wallet.getNewAddress()
    }

    /**
     * Returns list of transactions in JSON format
     * @see WalletController.getTransactions
     */
    fun getTransactions(descriptor: String, networkIn: String): String {

        val network : Network = Network.valueOf(networkIn)

        // Set up bdk::wallet object
        val wallet = Wallet(descriptor, null, network, db, client)

        // sync balance of descriptor
        wallet.sync(progressUpdate = NullProgress, maxAddressParam = null)

        // get transactions and sort by block height
        val transactionList = wallet.getTransactions()
        val transactionSorted = transactionList.sortedWith(transactionCompByHeight)

        // map transactionList of Transaction objects into JSON using Jackson
        val mapper = jacksonObjectMapper()
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(transactionSorted)
    }

    /**
     * Creates partially signed bitcoin transaction for the wallet.
     * @see WalletController.createPSBT
     */
    fun createUnsignedPSBT(descriptor: String, networkIn: String, recipient: String, amount: ULong, feeRate: Float?) : String {

        val network : Network = Network.valueOf(networkIn)

        // Set up bdk::wallet object and sync wallet before spending
        val wallet = Wallet(descriptor, null, network, db, client)
        wallet.sync(progressUpdate = NullProgress, maxAddressParam = null)

        // Create PSBT object and return it serialized
        val psbt = PartiallySignedBitcoinTransaction(wallet, recipient, amount, feeRate)
        return psbt.serialize()
    }

    /**
     * Broadcasts the signed transaction to the global bitcoin blockchain
     * @see WalletController.broadcast
     */
    fun broadcastSignedPSBT(descriptor: String, networkIn: String, psbtSerialized: String) : String {

        val network : Network = Network.valueOf(networkIn)

        // Set up bdk::wallet object
        val wallet = Wallet(descriptor, null, network, db, client)

        // Deserialize PSBT o bject and broadcast, PSBT must be signed by all relevant parties before broadcasting
        val psbt = PartiallySignedBitcoinTransaction.deserialize(psbtSerialized)

        val txid = wallet.broadcast(psbt)

        return "https://mempool.space/testnet/tx/$txid"
    }

    /**
     * Decodes base64 string into normal string
     * @param stringIn A base64 string
     * @return normal String that is to was decoded from the base64 string
     */
    fun decodeBase64(stringIn: String) : String {
        return  String(Base64.getDecoder().decode(stringIn))
    }

    /**
     *  Encodes normal string into base64 string
     *  @param stringIn A normal string
     *  @return A base65 string that was transformed from the normal version
     */
    fun encodeBase64(stringIn: String) : String {
        return Base64.getEncoder().encodeToString(stringIn.toByteArray())
    }

    /**
     * Validates descriptor and network
     * @throws BdkException if either is invalid
     */
    fun validateDescriptor(descriptor: String, networkIn: String) {
        val network : Network = Network.valueOf(networkIn)
        val wallet = Wallet(descriptor, null, network, db, client)
    }
}