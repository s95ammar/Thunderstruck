package com.s95ammar.thunderstruck.model.datasource.remote

import com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.dto.ForecastDto
import com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.dto.LocationInfoDto
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getFiveDayForecast(locationKey: String): Response<ForecastDto>

    suspend fun getCitySearchResults(query: String): Response<List<LocationInfoDto>>
}
