package fk.LocateMeServer.Exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class UserNotFoundForIdException(userId: Long) : RuntimeException("Could not find user with id: $userId")

@ResponseStatus(HttpStatus.NOT_FOUND)
class UserNotFoundForTokenException(userToken: String) : RuntimeException("Could not find user with token: $userToken")