package com.s95ammar.thunderstruck.model.datasource.local

import androidx.room.withTransaction
import com.s95ammar.thunderstruck.model.datasource.local.db.ThunderstruckDb
import com.s95ammar.thunderstruck.model.datasource.local.db.entity.DailyForecastEntity
import com.s95ammar.thunderstruck.model.datasource.local.sharedprefs.SharedPrefsManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val sharedPrefsManager: SharedPrefsManager,
    private val db: ThunderstruckDb
) {

    suspend fun deleteAllAndInsert(forecastEntityList: List<DailyForecastEntity>) {
        db.withTransaction {
            db.forecastDao.deleteAllDailyForecasts()
            db.forecastDao.insert(forecastEntityList)
        }
    }

    fun getFullDailyForecastEntityList(): Flow<List<DailyForecastEntity>> {
        return db.forecastDao.getFullDailyForecastEntityList()
    }

}