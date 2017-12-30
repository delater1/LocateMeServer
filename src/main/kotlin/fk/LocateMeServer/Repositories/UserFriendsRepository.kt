package fk.LocateMeServer.Repositories

import fk.LocateMeServer.Domain.User
import org.springframework.data.jpa.repository.JpaRepository
import fk.LocateMeServer.Domain.UserFriends
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional


interface UserFriendsRepository : JpaRepository<UserFriends, Long> {

    @Query("SELECT u FROM User u WHERE u.id IN (SELECT uf.userFriend.id FROM UserFriends uf WHERE uf.user.id = :userId)")
    fun getUserFriends(@Param("userId") userId: Long): List<User>

    @Transactional
    @Modifying
    @Query("DELETE FROM UserFriends WHERE user.id = :userId")
    fun deleteUserFriends(@Param("userId") userId: Long)
}