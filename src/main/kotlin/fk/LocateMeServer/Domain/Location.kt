package fk.LocateMeServer.Domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

/**
 * Created by korpa on 08.10.2017.
 */
@Entity
@Table(name = "Locations")
class Location() {
    constructor(id: Long, user: User, time: Long, latitude: Double, longitude: Double) : this() {
        this.id = id
        this.user = user
        this.time = time
        this.latitude = latitude
        this.longitude = longitude
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long = 0
    @JsonIgnore
    @ManyToOne(cascade = [(CascadeType.ALL)])
    var user: User = User()
    @Transient
    var userId: Long = 0
        get() = user.id
    @Column(name = "time", nullable = false)
    var time: Long = 0L
    @Column(name = "latitude", nullable = false)
    var latitude: Double = 0.0
    @Column(name = "longitude", nullable = false)
    var longitude: Double = 0.0
}