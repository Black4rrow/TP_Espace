package com.example.tp_mobile.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class Apod (
    @SerialName("copyright"       ) var copyright      : String? = null,
    @SerialName("date"            ) var date           : String? = null,
    @SerialName("explanation"     ) var explanation    : String? = null,
    @SerialName("hdurl"           ) var hdurl          : String? = null,
    @SerialName("media_type"      ) var mediaType      : String? = null,
    @SerialName("service_version" ) var serviceVersion : String? = null,
    @SerialName("title"           ) var title          : String? = null,
    @SerialName("url"             ) var url            : String? = null

)