package com.s95ammar.thunderstruck.model.repository

import com.s95ammar.thunderstruck.model.datasource.Resource
import com.s95ammar.thunderstruck.model.datasource.local.LocalDataSource
import com.s95ammar.thunderstruck.model.datasource.local.db.entity.DailyForecastEntity
import com.s95ammar.thunderstruck.model.datasource.networkBoundResource
import com.s95ammar.thunderstruck.model.datasource.remote.RemoteDataSource
import com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.isDataFresh
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ForecastRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    fun saveLocationKey(locationKey: String) {
        localDataSource.saveLocationKey(locationKey)
    }

    fun getLocationKey() = localDataSource.getLocationKey()

    fun getFiveDayForecast(locationKey: String, forceUpdate: Boolean = false): Flow<Resource<List<DailyForecastEntity>>> {
        return networkBoundResource(
            queryFlow = localDataSource.getFullDailyForecastEntityList(),
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
            }
        )
    }


}