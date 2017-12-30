package fk.LocateMeServer.Domain

import javax.persistence.*

@Entity
@Table(name = "UserFriends")
class UserFriends() {
    constructor(user: User, userFriend: User, alias: String) : this() {
        this.user = user
        this.userFriend = userFriend
        this.alias = alias
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: Long = 0
    @ManyToOne
    @JoinColumn(name = "userId")
    var user: User = User()
    @ManyToOne
    @JoinColumn(name = "userFriendId")
    var userFriend: User = User()
    @Column(name = "alias")
    var alias: String = ""
}