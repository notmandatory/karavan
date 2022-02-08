package org.bitcoindevkit.karavan

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.util.WebUtils
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import com.fasterxml.jackson.module.kotlin.*
import org.bitcoindevkit.*


@Service
class WalletService {

    fun getBalance(descriptor: String, networkIn: String): String{

        val db = DatabaseConfig.Memory("")
        val network : Network
        val balance : ULong

        if (networkIn.equals("TESTNET", ignoreCase = true))
            network = Network.TESTNET
        else
            return "Invalid Network: $networkIn!"

        val client =
            BlockchainConfig.Electrum(
                ElectrumConfig("ssl://electrum.blockstream.info:60002", null, 5u, null, 10u)
            )
        val wallet = OnlineWallet(descriptor, null, network, db, client)

        // Cannot find good docs on bdk:sync function, find out what parameters are needed
        //wallet.sync(progressUpdate = BdkProgress.?, maxAddressParam = ?)

        // get the balance
        balance = wallet.getBalance()

        // put balance into JSON and return it
        val mapper = ObjectMapper()
        val balanceJSON: ObjectNode = mapper.createObjectNode()
        balanceJSON.put("balance", balance.toString())
        val balanceJSONString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(balanceJSON)

        return balanceJSONString
    }

    fun WalletToJSON(walletIn: Wallet): String{
        val mapper = jacksonObjectMapper()
        var jsonStr : String = mapper.writeValueAsString(walletIn)
        return jsonStr
    }


}