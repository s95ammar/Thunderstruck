package com.s95ammar.thunderstruck.model.datasource.remote

import com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.ApiService
import com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.dto.ForecastDto
import com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.dto.LocationInfoDto
import com.s95ammar.thunderstruck.security.Security
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : RemoteDataSource {

    override suspend fun getFiveDayForecast(locationKey: String): Response<ForecastDto> {
        return apiService.getFiveDayForecast(locationKey, metric = true, Security.API_KEY)
    }

    override suspend fun getCitySearchResults(query: String): Response<List<LocationInfoDto>> {
        return apiService.getCitySearchResults(query, Security.API_KEY)
    }
}
