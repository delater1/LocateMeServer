package fk.LocateMeServer

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by korpa on 08.10.2017.
 */
@RestController
class Hello {

    @RequestMapping("/")
    fun index(): String {
        return "Ok"
    }
}