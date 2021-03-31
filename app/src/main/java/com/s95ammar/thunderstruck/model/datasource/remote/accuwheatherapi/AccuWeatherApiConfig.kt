package com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi

import com.s95ammar.thunderstruck.security.Security

object AccuWeatherApiConfig {
    const val BASE_URL = "http://dataservice.accuweather.com/"
    const val TIMEOUT_SECONDS = 30L
    const val HEADER_KEY_API_KEY = "apiKey"
    const val HEADER_VALUE_API_KEY = Security.API_KEY
}