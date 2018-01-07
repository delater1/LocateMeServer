package fk.LocateMeServer.RestControllers.Dto

class UserFriendDTO() {
    constructor(userId: Long, userFriendId: Long, token: String, alias: String) : this() {
        this.userId = userId
        this.userFriendId = userFriendId
        this.token = token
        this.alias = alias
    }

    var userId: Long = -1
    var userFriendId: Long = -1
    var token: String = ""
    var alias: String = ""
}