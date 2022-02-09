package org.bitcoindevkit.karavan

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import org.bitcoindevkit.*
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.WebUtils
import java.util.*
import java.util.stream.Collectors
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.reflect.full.memberProperties


@SpringBootApplication
class Karavan

fun main(args: Array<String>) {
    runApplication<Karavan>(*args)
}

@RestController
@RequestMapping("/wallet")
class WalletController(val walletService: WalletService) {

    // Open Wallet by storing the wallet payload in a browser session cookie
    @PutMapping
    fun openWallet(response: HttpServletResponse, request: HttpServletRequest, @RequestBody payload: Wallet): String? {
        // stores payload of type wallet into a cookie on the client's side
        setCookie(response, payload)
        return "Wallet is opened!"
    }

    // Close wallet by dropping existing descriptor cookie
    @DeleteMapping
    fun closeWallet(response: HttpServletResponse, request: HttpServletRequest?): String?{

        val cookie = WebUtils.getCookie(request!!, "descriptor")
        return if (cookie == null) {
            "Wallet not found!"
        }
        else if(cookie.value.isNullOrEmpty()){
            "Wallet already closed!"
        }
        else {
            // In order to delete a cookie, we must replicate it, set its maxAge to 0 and add it to response
            val cookieReplacement = Cookie(cookie.name, null)
            cookieReplacement.path = "/wallet"
            cookieReplacement.isHttpOnly = true
            cookie.maxAge = 0
            response.addCookie(cookieReplacement)
            "Wallet is closed!"
        }
    }

    // Get the wallet's balance
    // Use the browser cookie to get the network and descriptor and then syncing wallet
    // return a balance json with the balance amount.
    @GetMapping("/balance")
    fun getBalance(request: HttpServletRequest): String{

        // Retrieve wallet cookies
        val descCookie = WebUtils.getCookie(request, "descriptor")
        val networkCookie = WebUtils.getCookie(request, "network")

        // check if cookies are null
        if (descCookie == null || networkCookie == null){
            return "Wallet not found."
        }
        // check if cookie values are dropped
        if (descCookie.value.isNullOrEmpty() || networkCookie.value.isNullOrEmpty()){
            return "Wallet is closed, cannot get balance!"
        }

        val descriptor = descCookie.value
        val network = networkCookie.value

        // Call getBalance from WalletService class to process logic and return balance JSON
        return walletService.getBalance(descriptor, network)
    }

    // Store wallet object into client's cookie session
    fun setCookie(response: HttpServletResponse, walletIn: Wallet) {

        // create cookie key-value pairs for every wallet data member
        for (dataMember in Wallet::class.memberProperties) {
            val cookie = Cookie(dataMember.name, dataMember.get(walletIn) as String?)
            // set httpOnly to true so cookie can only be accessed by server (to prevent XSS attacks)
            cookie.isHttpOnly = true
            // set cookie scope to "/wallet"
            cookie.path = "/wallet"
            response.addCookie(cookie)
        }
    }

    // Read a single cookie value based on key input
    fun readCookie(request: HttpServletRequest?, key: String): String? {
        val cookie = WebUtils.getCookie(request!!, key)
        return if (cookie != null) {
            "Wallet value is ${cookie.value}"
        } else {
            "Wallet not found!"
        }
    }

    // Read all cookie inside the client's web session
    fun readAllCookies(request: HttpServletRequest?): String? {

        val cookies = request!!.cookies
        return if (cookies != null) {
            Arrays.stream(cookies)
                .map { c -> c.getName() + "=" + c.getValue() }.collect(Collectors.joining(", "))
        } else {
            "No cookies"
        }
    }

}
