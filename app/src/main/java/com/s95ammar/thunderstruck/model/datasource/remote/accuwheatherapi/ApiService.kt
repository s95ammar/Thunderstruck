package com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi

import com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.dto.ForecastDto
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("forecasts/v1/daily/5day/")
    suspend fun getFiveDayForecast(): Response<ForecastDto>
}