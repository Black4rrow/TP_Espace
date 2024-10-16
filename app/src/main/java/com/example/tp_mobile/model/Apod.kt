package com.example.tp_mobile.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Apod(
    var copyright: String? = null,
    var date: String? = null,
    var explanation: String? = null,
    var hdurl: String? = null,
    @SerialName("media_type")
    var mediaType: String? = null,
    @SerialName("service_version")
    var serviceVersion: String? = null,
    var title: String? = null,
    var url: String? = null,

)