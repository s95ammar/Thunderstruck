package com.s95ammar.thunderstruck.model.datasource.remote

import com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.ApiService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    fun getFiveDayForecast() = flow {
        emit(apiService.getFiveDayForecast())
    }

}