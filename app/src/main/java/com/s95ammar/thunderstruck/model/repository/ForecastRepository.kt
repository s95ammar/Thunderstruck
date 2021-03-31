package com.s95ammar.thunderstruck.model.repository

import com.s95ammar.thunderstruck.model.datasource.local.LocalDataSource
import com.s95ammar.thunderstruck.model.datasource.remote.RemoteDataSource
import javax.inject.Inject

class ForecastRepository @Inject constructor(
    val localDataSource: LocalDataSource,
    val remoteDataSource: RemoteDataSource
) {
}