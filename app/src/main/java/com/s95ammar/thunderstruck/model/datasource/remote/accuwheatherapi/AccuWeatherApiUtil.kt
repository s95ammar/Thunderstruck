package com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi

fun isDataFresh(date: Long): Boolean {
    return System.currentTimeMillis() - date <= AccuWeatherApiConfig.CACHE_MAX_AGE_MILLIS
}
