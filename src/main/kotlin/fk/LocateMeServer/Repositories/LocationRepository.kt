package fk.LocateMeServer.Repositories

import fk.LocateMeServer.Domain.Location
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface LocationRepository : JpaRepository<Location, Long> {

    @Query("SELECT l FROM Location l WHERE l.user.id = :userId AND l.time >= :startTime AND l.time <= :endTime")
    fun getLocationsForUserIdFromTimePeirod(@Param("userId") userId: Long,
                                            @Param("startTime") startTime: Long,
                                            @Param("endTime") endTime: Long): List<Location>

    @Query("SELECT l FROM Location l WHERE l.user.id = :userId ORDER BY l.time ASC")
    fun getLocationsForUserId(@Param("userId") userId: Long): List<Location>

    @Query("SELECT * FROM locations l" +
            "  JOIN user_friends uf ON l.user_id = uf.user_friend_id" +
            "  JOIN (SELECT MAX(time),user_id,MAX(id) AS newestLocationId FROM locations GROUP BY user_id) loc ON loc.newestLocationId = l.id" +
            " WHERE uf.user_id = :userId", nativeQuery = true)
    fun getLastUserFriendsLocations(@Param("userId") userId: Long): List<Location>
}