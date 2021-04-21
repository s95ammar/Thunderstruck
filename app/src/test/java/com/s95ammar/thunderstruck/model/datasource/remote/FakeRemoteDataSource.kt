package com.s95ammar.thunderstruck.model.datasource.remote

import com.s95ammar.thunderstruck.model.FakeData
import com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.dto.ForecastDto
import com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.dto.LocationInfoDto
import kotlinx.coroutines.delay
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class FakeRemoteDataSource : RemoteDataSource {

    companion object {
        const val DEFAULT_ERROR_CODE = 500
        const val DEFAULT_ERROR_DESC = "Internal server error"
    }

    var shouldReturnError = false

    override suspend fun getFiveDayForecast(locationKey: String): Response<ForecastDto> {
        return if (shouldReturnError) {
            Response.error(DEFAULT_ERROR_CODE, DEFAULT_ERROR_DESC.toResponseBody())
        } else {
            delay(10)
            Response.success(FakeData.remoteDailyForecastResponse)
        }
    }

    override suspend fun getCitySearchResults(query: String): Response<List<LocationInfoDto>> {
        return if (shouldReturnError) {
            Response.error(DEFAULT_ERROR_CODE, DEFAULT_ERROR_DESC.toResponseBody())
        } else {
            Response.success(listOf(FakeData.dummyLocationInfoDto))
        }
    }
}
