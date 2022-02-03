package org.bitcoindevkit.karavan

import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.util.WebUtils
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Service
class WalletService {

    fun openWallet(response: HttpServletResponse, walletIn: Wallet){
        setCookie(response, walletIn)
    }

    fun setCookie(response: HttpServletResponse, walletIn: Wallet) {
        // create a cookie
        val cookie = Cookie("wallet", walletIn.descriptor) // might need to change to springframework cookie instead of java
        cookie.isHttpOnly = true

        //add cookie to response
        response.addCookie(cookie)
    }

    fun readCookie(request: HttpServletRequest?, key: String): String? {
        val cookie = WebUtils.getCookie(request!!, key)
        return if (cookie != null) {
            "Wallet value is ${cookie.value}"
        } else {
            "Wallet not found!"
        }
    }

}