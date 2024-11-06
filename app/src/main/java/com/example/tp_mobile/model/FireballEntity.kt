package com.example.tp_mobile.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorites")
data class FireballEntity(
    var date: String,
    var energy: Double?,
    var impactE: Double?,
    var vel: Int?,
    var alt: Double?,
    var lon: Int,
    var lat: Int,
    var lonDir: String?,
    var latDir: String?,
) : Serializable {
    @PrimaryKey
    var compositeKey: String = "$date-$lon-$lat"
}