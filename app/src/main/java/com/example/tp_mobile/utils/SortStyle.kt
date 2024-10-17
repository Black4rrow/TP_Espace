package com.example.tp_mobile.utils

enum class SortStyle( val orderValueApi: String?) {
    NONE(null),
    DATE_ASC("date asc"),
    DATE_DESC("date desc"),
    SPEED_ASC("vel asc"),
    SPEED_DESC("vel desc"),
    RADIATED_ENERGY_ASC("energy asc"),
    RADIATED_ENERGY_DESC("energy desc"),
    FAVORITES(null)
}