package org.bitcoindevkit.karavan

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@SpringBootApplication
class Karavan

fun main(args: Array<String>) {
    runApplication<Karavan>(*args)
}

@RestController
@RequestMapping("/wallet")
class WalletController() {

    // TODO remove this example
    @GetMapping("/hello/{name}")
    fun hello(@PathVariable name: String): String {
        return "hello $name"
    }
}
