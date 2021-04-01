package com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi

import com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.dto.ForecastDto
import com.s95ammar.thunderstruck.security.Security
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("forecasts/v1/daily/5day/{locationKey}?apikey=${Security.API_KEY}")
    suspend fun getFiveDayForecast(@Path("locationKey") locationKey: String): Response<ForecastDto>
}