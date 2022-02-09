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
        // read cookie with key 'wallet' to confirm value
        return readAllCookies(request)
    }

    // Close wallet by dropping existing descriptor cookie by setting max-age to 0
    @DeleteMapping
    fun closeWallet(request: HttpServletRequest?): String?{

        val cookie = WebUtils.getCookie(request!!, "descriptor")
        return if (cookie != null) {
            cookie.maxAge = 0
            "Wallet is Closed!"
        } else {
            "Wallet not found!"
        }
    }

    // Get the wallet's balance
    // Use the browser cookie to get the network and descriptor and then sync
    // return a balance json with the balance amount.
    @GetMapping("/balance")
    fun getBalance(request: HttpServletRequest): String{

        // Retrieve wallet cookies and check if they are null before we proceed
        val descCookie = WebUtils.getCookie(request, "descriptor")
        val networkCookie = WebUtils.getCookie(request, "network")

        if (descCookie == null || networkCookie == null){
            return "Wallet cookie not found or corrupted."
        }

        val descriptor = descCookie.value
        val network = networkCookie.value

        return walletService.getBalance(descriptor, network)
    }

    // Store wallet object into client's cookie session
    fun setCookie(response: HttpServletResponse, walletIn: Wallet) {

        // create cookie key-value pairs for every wallet data member and set HTTP Only property to true
        for (dataMember in Wallet::class.memberProperties) {
            val cookie = Cookie(dataMember.name, dataMember.get(walletIn) as String?)
            cookie.isHttpOnly = true
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
