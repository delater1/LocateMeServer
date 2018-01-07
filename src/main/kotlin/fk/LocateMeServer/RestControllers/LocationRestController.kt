package fk.LocateMeServer.RestControllers

import fk.LocateMeServer.Domain.Location
import fk.LocateMeServer.Repositories.LocationRepository
import fk.LocateMeServer.Repositories.UserRepository
import fk.LocateMeServer.Utils.LocationIterator
import org.apache.commons.logging.LogFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("user/{userId}/location")
class LocationRestController(val locationRepository: LocationRepository, val userRepository: UserRepository) {
    val log = LogFactory.getLog(this.javaClass)

    @RequestMapping(method = [(RequestMethod.GET)])
    fun getUserLocations(@PathVariable userId: Long): List<Location> {
        log.info("getting UserLocations for User with id: $userId")
        return locationRepository.getLocationsForUserId(userId)
    }

    @RequestMapping(method = [(RequestMethod.GET)], value = ["/{startTime}", "/{endTime}"])
    fun getUserLocationsWithinTimePeriod(@PathVariable userId: Long, @PathVariable startTime: Long, @PathVariable endTime: Long): List<Location> {
        return locationRepository.getLocationsForUserIdFromTimePeirod(userId, startTime, endTime)
    }

    @RequestMapping(method = [(RequestMethod.POST)])
    fun addLocationForUser(@PathVariable userId: Long, @RequestBody location: Location) {
        log.info("adding new Location: $location")
        val user = userRepository.getOne(userId)
        location.user = user
        locationRepository.save(location)
    }

    @RequestMapping(method = [(RequestMethod.GET)], value = ["/lastFriendsLocations"])
    fun getUserFriendsLocationsAfter(@PathVariable userId: Long): List<Location> {
        return locationRepository.getLastUserFriendsLocations(userId)
    }

    @RequestMapping(method = [(RequestMethod.GET)], value = ["/last24HBucketed"])
    fun getUserLocationsInLast24HBucketed(@PathVariable userId: Long): MutableList<Location?> {
        return getUserLocationsWithinLast24HWithinBuckets(10, userId)
    }

    fun getUserLocationsWithinLast24HWithinBuckets(bucketMinutes: Int, userId: Long): MutableList<Location?> {
        val currentTime = System.currentTimeMillis()
        val locationsWithinLast24H = locationRepository.getLocationsForUserIdFromTimePeirod(userId, getTime24HAgo(currentTime), currentTime)
        return getBucketedLocations(currentTime, bucketMinutes, locationsWithinLast24H)
    }

    private fun getBucketedLocations(currentTime: Long, bucketMinutes: Int, locationsWithinLast24H: List<Location>): MutableList<Location?> {
        val locationIterator = LocationIterator(
                getTime24HAgo(currentTime),
                currentTime,
                minutesToMilis(bucketMinutes),
                locationsWithinLast24H)
        val bucketedLocationsList = mutableListOf<Location?>()

        while (locationIterator.hasNext()) {
            val bucketLocation = locationIterator.getMiddleLocationWithinCurrentBucket()
            bucketedLocationsList.add(bucketLocation)
            locationIterator.increment()
        }
        return bucketedLocationsList
    }

    private fun minutesToMilis(bucketMinutes: Int) = bucketMinutes.toLong() * 60 * 1000


    fun getTime24HAgo(currentTime: Long): Long {
        return currentTime - (24 * 60 * 60 * 1000)
    }
}

