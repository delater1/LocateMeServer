package fk.LocateMeServer.RestControllers

import fk.LocateMeServer.Domain.User
import fk.LocateMeServer.Domain.UserFriends
import fk.LocateMeServer.Exceptions.UserNotFoundForIdException
import fk.LocateMeServer.Exceptions.UserNotFoundForTokenException
import fk.LocateMeServer.Repositories.UserFriendsRepository
import fk.LocateMeServer.Repositories.UserRepository
import fk.LocateMeServer.RestControllers.Dto.UserFriendDTO
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user/{userId}/friends")
class UserFriendsRestController @Autowired constructor(val userRepository: UserRepository, val userFriendsRepository: UserFriendsRepository) {
    val log = LogFactory.getLog(this.javaClass)

    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun addUserFriend(@PathVariable userId: Long, @RequestBody userFriendDTO: UserFriendDTO): UserFriendDTO {
        val userFriend = userRepository.findUserByToken(userFriendDTO.token) ?: throw UserNotFoundForTokenException(userFriendDTO.token)
        val user = userRepository.findOne(userId) ?: throw UserNotFoundForIdException(userId)
        val newUserFriend = UserFriends(user, userFriend, userFriendDTO.alias)
        userFriendsRepository.save(newUserFriend)
        return UserFriendDTO(userId, userFriend.id, userFriend.token, newUserFriend.alias)
    }

    @RequestMapping(method = [(RequestMethod.GET)])
    fun getUserFriends(@PathVariable userId: Long): List<User> {
        return userFriendsRepository.getUserFriends(userId)
    }
}