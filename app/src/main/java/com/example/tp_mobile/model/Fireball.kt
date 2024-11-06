package com.example.tp_mobile.model

import com.example.tp_mobile.views.fireball.CustomFireballAdapter
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import java.io.Serializable

data class FireballApiResponse(
    @SerializedName("total_count") var totalCount: Int? = null,
    @SerializedName("results") var results: ArrayList<Fireball> = arrayListOf()
) : Serializable


data class Fireball(
    @SerializedName("date") var date: String? = null,
    @SerializedName("energy") var energy: Double? = null,
    @SerializedName("impact_e") var impactE: Double? = null,
    @SerializedName("vel") var vel: Int? = null,
    @SerializedName("alt") var alt: Double? = null,
    @SerializedName("lon") var lon: Int = 0,
    @SerializedName("lat") var lat: Int = 0,
    @SerializedName("lon_dir") var lonDir: String? = null,
    @SerializedName("lat_dir") var latDir: String? = null,
    @SerializedName("coord") var coord: Coord? = Coord(),
) : Serializable {
    var isFavorite: Boolean = false

    fun getDBId(): String {
        return this.date + "-" +  this.lon + "-" + this.lat
    }
}

data class Coord(
    @SerializedName("lon") var lon: Double? = null,
    @SerializedName("lat") var lat: Double? = null
) : Serializable

interface OnFireballFavoriteListener {
    fun onFavoriteClicked(fireball: Fireball, holder: CustomFireballAdapter.FireballViewHolder, position: Int)
}