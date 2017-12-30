package fk.LocateMeServer.RestControllers

import fk.LocateMeServer.Domain.Location
import fk.LocateMeServer.Repositories.LocationRepository
import fk.LocateMeServer.Repositories.UserRepository
import org.apache.commons.logging.LogFactory
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("user/{userId}/location")
class LocationRestController(val locationRepository: LocationRepository, val userRepository: UserRepository) {
    val log = LogFactory.getLog(this.javaClass)

    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun getUserLocations(@PathVariable userId: Long): List<Location> {
        log.info("getting UserLocations for User with id: $userId")
        return locationRepository.getLocationsForUserId(userId)
    }

    @RequestMapping(method = arrayOf(RequestMethod.GET), value = *arrayOf("/{startTime}", "/{endTime}"))
    fun getUserLocationsWithinTimePeriod(@PathVariable userId: Long, @PathVariable startTime: Long, @PathVariable endTime: Long): List<Location> {
        return locationRepository.getLocationsForUserIdFromTimePeirod(userId, Date(startTime), Date(endTime))
    }

    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun addLocationForUser(@PathVariable userId: Long, @RequestBody location: Location) {
        log.info("adding new Location: $location")
        val user = userRepository.getOne(userId)
        location.user = user
        locationRepository.save(location)
    }

    @RequestMapping(method = arrayOf(RequestMethod.GET), value = *arrayOf("/lastFriendsLocations"))
    fun getUserFriendsLocationsAfter(@PathVariable userId: Long): List<Location> {
        return locationRepository.getLastUserFriendsLocations(userId)
    }
}

