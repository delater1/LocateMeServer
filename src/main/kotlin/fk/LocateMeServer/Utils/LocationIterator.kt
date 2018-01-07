package fk.LocateMeServer.Utils

import fk.LocateMeServer.Domain.Location

class LocationIterator(val startTime: Long, val endTime: Long, val bucketMilis: Long, val locations: List<Location>) {
    private var currentStart = startTime
    private var currentEnd = startTime + bucketMilis

    fun getMiddleLocationWithinCurrentBucket(): Location? {
        val locationsInCurrentBucket = getLocationsWithinTimeConstraints(currentStart, currentEnd)
        if (locationsInCurrentBucket.isEmpty())
            return null

        return locationsInCurrentBucket[locationsInCurrentBucket.size / 2]
    }

    fun increment() {
        currentStart = currentEnd
        currentEnd += bucketMilis
    }

    private fun getLocationsWithinTimeConstraints(startTime: Long, endTime: Long): List<Location> {
        return locations.filter { it.time in startTime..endTime }
    }

    fun hasNext(): Boolean {
        return currentEnd <= endTime
    }
}