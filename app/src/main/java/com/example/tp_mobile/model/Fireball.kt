package com.example.tp_mobile.model

import com.example.tp_mobile.views.fireball.CustomFireballAdapter
import kotlinx.serialization.SerialName
import java.io.Serializable

data class FireballApiResponse(
    @SerialName("total_count") var totalCount: Int? = null,
    @SerialName("results") var results: ArrayList<Fireball> = arrayListOf()
) : Serializable


data class Fireball(
    @SerialName("date") var date: String? = null,
    @SerialName("energy") var energy: Double? = null,
    @SerialName("impact_e") var impactE: Double? = null,
    @SerialName("vel") var vel: Int? = null,
    @SerialName("alt") var alt: Double? = null,
    @SerialName("lon") var lon: Int = 0,
    @SerialName("lat") var lat: Int = 0,
    @SerialName("lon_dir") var lonDir: String? = null,
    @SerialName("lat_dir") var latDir: String? = null,
    @SerialName("coord") var coord: Coord? = Coord(),
) : Serializable {
    var isFavorite: Boolean = false

    fun getDBId(): String {
        return this.date + "-" +  this.lon + "-" + this.lat
    }
}

data class Coord(
    @SerialName("lon") var lon: Double? = null,
    @SerialName("lat") var lat: Double? = null
) : Serializable

interface OnFireballFavoriteListener {
    fun onFavoriteClicked(fireball: Fireball, holder: CustomFireballAdapter.FireballViewHolder, position: Int)
}