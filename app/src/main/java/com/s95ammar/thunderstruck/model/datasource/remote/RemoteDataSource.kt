package com.s95ammar.thunderstruck.model.datasource.remote

import com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.ApiService
import com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.dto.ForecastDto
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getFiveDayForecast(locationKey: String): Response<ForecastDto> {
        return apiService.getFiveDayForecast(locationKey)
    }

}