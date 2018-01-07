package fk.LocateMeServer.Domain

import javax.persistence.*

/**
 * Created by korpa on 08.10.2017.
 */
@Entity
@Table(name = "Users")
class User() {
    constructor(device: String, manufacturer: String) : this() {
        this.token = token
        this.device = device
        this.manufacturer = manufacturer
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: Long = 0
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "token", nullable = false, unique = true)
    var token: String = ""
    @Column(name = "device", nullable = false)
    var device: String = ""
    @Column(name = "manufacturer", nullable = false)
    var manufacturer: String = ""
}