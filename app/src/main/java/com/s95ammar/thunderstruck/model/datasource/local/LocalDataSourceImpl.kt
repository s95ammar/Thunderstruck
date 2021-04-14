package com.s95ammar.thunderstruck.model.datasource.local

import androidx.room.withTransaction
import com.s95ammar.thunderstruck.model.datasource.local.db.ThunderstruckDb
import com.s95ammar.thunderstruck.model.datasource.local.db.dao.ForecastDao
import com.s95ammar.thunderstruck.model.datasource.local.db.entity.DailyForecastEntity
import com.s95ammar.thunderstruck.model.datasource.local.sharedprefs.SharedPrefsManager
import com.s95ammar.thunderstruck.ui.appscreens.location.data.LocationInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val sharedPrefsManager: SharedPrefsManager,
    private val db: ThunderstruckDb,
    private val forecastDao: ForecastDao
) : LocalDataSource {

    override suspend fun deleteAllAndInsert(forecastEntityList: List<DailyForecastEntity>) {
        db.withTransaction {
            forecastDao.deleteAllDailyForecasts()
            forecastDao.insert(forecastEntityList)
        }
    }

    override fun getFullDailyForecastEntityList(): Flow<List<DailyForecastEntity>> {
        return forecastDao.getFullDailyForecastEntityList()
    }

    override fun saveLocationInfo(locationInfo: LocationInfo) {
        sharedPrefsManager.saveLocationInfo(locationInfo)
    }

    override fun getLocationInfo(): LocationInfo? {
        return sharedPrefsManager.loadLocationInfo()
    }
}
