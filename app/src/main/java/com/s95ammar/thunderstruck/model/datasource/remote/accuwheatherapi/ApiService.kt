package com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi

import com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.dto.ForecastDto
import com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.dto.LocationInfoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("forecasts/v1/daily/5day/{locationKey}")
    suspend fun getFiveDayForecast(
        @Path("locationKey") locationKey: String,
        @Query("metric") metric: Boolean,
        @Query("apikey") apiKey: String
    ): Response<ForecastDto>

    @GET("locations/v1/cities/search")
    suspend fun getCitySearchResults(
        @Query("q") query: String,
        @Query("apikey") apiKey: String
    ): Response<List<LocationInfoDto>>


}