package fk.LocateMeServer.RestControllers

import fk.LocateMeServer.Domain.User
import fk.LocateMeServer.Exceptions.UserNotFoundForIdException
import fk.LocateMeServer.Repositories.UserRepository
import org.apache.commons.lang3.RandomStringUtils
import org.apache.commons.logging.LogFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserRestController(val userRepository: UserRepository) {
    val log = LogFactory.getLog(javaClass)
    val TAG_LENGTH = 6

    @RequestMapping(method = arrayOf(RequestMethod.GET), value = ["/all"])
    fun getUsers(): List<User> {
        log.info("getting Users")
        return userRepository.findAll()
    }

    @RequestMapping(method = arrayOf(RequestMethod.GET), value = ["/id/{id}"])
    fun getUserById(@PathVariable id: Long): User {
        log.info("getting User with Id: $id")
        return userRepository.findOne(id) ?: throw UserNotFoundForIdException(id)
    }

    @RequestMapping(method = arrayOf(RequestMethod.GET), value = ["/token/{token}"])
    fun getUserByToken(@PathVariable token: String): User? {
        return userRepository.findUserByToken(token)
    }

    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun addUser(@RequestBody user: User): User {
        user.token = generateRandomToken()
        return userRepository.save(user)
    }

    fun generateRandomToken(): String {
        var token = getRandomAlphanumericInUppercase()
        while (userRepository.findUserByToken(token) != null) {
            token = getRandomAlphanumericInUppercase()
        }
        return token
    }

    private fun getRandomAlphanumericInUppercase() = RandomStringUtils.randomAlphanumeric(TAG_LENGTH).toUpperCase()
}


