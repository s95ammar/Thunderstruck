package com.s95ammar.thunderstruck.model.repository

import com.s95ammar.thunderstruck.model.datasource.Resource
import com.s95ammar.thunderstruck.model.datasource.local.LocalDataSource
import com.s95ammar.thunderstruck.model.datasource.local.db.entity.DailyForecastEntity
import com.s95ammar.thunderstruck.model.datasource.networkBoundResource
import com.s95ammar.thunderstruck.model.datasource.parseResponse
import com.s95ammar.thunderstruck.model.datasource.remote.RemoteDataSource
import com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.dto.LocationInfoDto
import com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.isDataFresh
import com.s95ammar.thunderstruck.ui.appscreens.location.data.LocationInfo
import com.s95ammar.thunderstruck.util.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ForecastRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val dispatcherProvider: DispatcherProvider
) {

    fun saveLocationInfo(locationInfo: LocationInfo) {
        localDataSource.saveLocationInfo(locationInfo)
    }

    fun getLocationInfo() = localDataSource.getLocationInfo()

    fun getFiveDayForecast(locationKey: String, forceUpdate: Boolean = false): Flow<Resource<List<DailyForecastEntity>>> {
        return networkBoundResource(
            queryFlow = { localDataSource.getFullDailyForecastEntityList() },
            fetch = { remoteDataSource.getFiveDayForecast(locationKey) },
            insert = { forecastDto ->
                val entityList = forecastDto.dailyForecasts.orEmpty().mapNotNull {
                    DailyForecastEntity.DtoMapper.toEntity(it)
                }
                localDataSource.deleteAllAndInsert(entityList)
            },
            shouldFetch = { dailyForecastEntityList ->

                val isCacheOutdated = dailyForecastEntityList.any { entity -> !isDataFresh(entity.createdTimestampUnixMs) }

                forceUpdate || dailyForecastEntityList.isEmpty() || isCacheOutdated
            },
            ioDispatcher = dispatcherProvider.io
        )
    }

    fun getCitySearchResults(query: String) = flow<Resource<List<LocationInfoDto>>> {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(remoteDataSource.getCitySearchResults(query).parseResponse()))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }.flowOn(dispatcherProvider.io)
}
