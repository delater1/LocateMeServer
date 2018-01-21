package fk.LocateMeServer

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Hello {

    @RequestMapping("/")
    fun index(): String {
        return "Ok"
    }
}