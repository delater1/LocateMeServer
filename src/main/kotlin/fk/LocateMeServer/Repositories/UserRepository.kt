package fk.LocateMeServer.Repositories

import fk.LocateMeServer.Domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface  UserRepository : JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE token = :token")
    fun findUserByToken(@Param("token") token: String): User?
}