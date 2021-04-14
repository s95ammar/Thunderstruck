package com.s95ammar.thunderstruck.model.datasource.local

import com.s95ammar.thunderstruck.model.datasource.local.db.entity.DailyForecastEntity
import com.s95ammar.thunderstruck.ui.appscreens.location.data.LocationInfo
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun deleteAllAndInsert(forecastEntityList: List<DailyForecastEntity>)
    fun getFullDailyForecastEntityList(): Flow<List<DailyForecastEntity>>
    fun saveLocationInfo(locationInfo: LocationInfo)
    fun getLocationInfo(): LocationInfo?
}
