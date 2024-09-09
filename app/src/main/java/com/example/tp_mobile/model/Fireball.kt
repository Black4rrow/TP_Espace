package com.example.tp_mobile.model
import com.fasterxml.jackson.annotation.JsonProperty

data class Fireball(
    @JsonProperty("date")
    val date: String,
    @JsonProperty("energy")
    val energy: Double,
    @JsonProperty("impact_e")
    val impactE: Double,
    @JsonProperty("vel")
    val velocity: Long,
    @JsonProperty("alt")
    val alt: Long,
    @JsonProperty("lon")
    val longitude: Long,
    @JsonProperty("lat")
    val latitude: Long,
    @JsonProperty("lon_dir")
    val lonDir: String,
    @JsonProperty("lat_dir")
    val latDir: String,
    @JsonProperty("coord")
    val coord: Coord,
)

data class Coord(
    val lon: Double,
    val lat: Double,
)
