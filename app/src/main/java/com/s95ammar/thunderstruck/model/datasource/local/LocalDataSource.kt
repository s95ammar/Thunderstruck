package com.s95ammar.thunderstruck.model.datasource.local

import androidx.room.withTransaction
import com.s95ammar.thunderstruck.model.datasource.local.db.ThunderstruckDb
import com.s95ammar.thunderstruck.model.datasource.local.db.dao.ForecastDao
import com.s95ammar.thunderstruck.model.datasource.local.db.entity.DailyForecastEntity
import com.s95ammar.thunderstruck.model.datasource.local.sharedprefs.SharedPrefsManager
import com.s95ammar.thunderstruck.ui.appscreens.location.data.LocationInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val sharedPrefsManager: SharedPrefsManager,
    private val db: ThunderstruckDb,
    private val forecastDao: ForecastDao
) {

    suspend fun deleteAllAndInsert(forecastEntityList: List<DailyForecastEntity>) {
        db.withTransaction {
            forecastDao.deleteAllDailyForecasts()
            forecastDao.insert(forecastEntityList)
        }
    }

    fun getFullDailyForecastEntityList(): Flow<List<DailyForecastEntity>> {
        return forecastDao.getFullDailyForecastEntityList()
    }

    fun saveLocationInfo(locationInfo: LocationInfo) {
        sharedPrefsManager.saveLocationInfo(locationInfo)
    }

    fun getLocationInfo(): LocationInfo? {
        return sharedPrefsManager.loadLocationInfo()
    }
}
