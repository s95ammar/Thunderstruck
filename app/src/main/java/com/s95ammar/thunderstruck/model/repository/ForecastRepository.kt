package com.s95ammar.thunderstruck.model.repository

import com.s95ammar.thunderstruck.model.datasource.Resource
import com.s95ammar.thunderstruck.model.datasource.local.LocalDataSource
import com.s95ammar.thunderstruck.model.datasource.local.db.entity.DailyForecastEntity
import com.s95ammar.thunderstruck.model.datasource.networkBoundResource
import com.s95ammar.thunderstruck.model.datasource.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ForecastRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    companion object {
        const val CACHE_MAX_AGE_MILLIS = 30 * 60 * 1000 // 30 min
    }

    fun getFiveDayForecast(locationKey: String = "324505" /*Kyiv location key. TODO: remove default value*/, forceUpdate: Boolean = false): Flow<Resource<List<DailyForecastEntity>>> {
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

                val isCacheOutdated = {
                    val currentTimeMillis = System.currentTimeMillis()

                    dailyForecastEntityList.any { entity ->
                        currentTimeMillis - entity.createdTimestampUnixMs > CACHE_MAX_AGE_MILLIS
                    }
                }

                forceUpdate || dailyForecastEntityList.isEmpty() || isCacheOutdated()
            }
        )
    }

}