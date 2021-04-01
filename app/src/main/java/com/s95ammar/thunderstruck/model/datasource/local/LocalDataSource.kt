package com.s95ammar.thunderstruck.model.datasource.local

import com.s95ammar.thunderstruck.model.datasource.local.db.dao.ForecastDao
import com.s95ammar.thunderstruck.model.datasource.local.db.entity.DailyForecastEntity
import com.s95ammar.thunderstruck.model.datasource.local.sharedprefs.SharedPrefsManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val sharedPrefsManager: SharedPrefsManager,
    private val forecastDao: ForecastDao
) {

    suspend fun insert(forecastEntity: DailyForecastEntity) {
        forecastDao.insert(forecastEntity)
    }

    suspend fun insert(forecastEntityList: List<DailyForecastEntity>) {
        forecastDao.insert(*forecastEntityList.toTypedArray())
    }

    fun getFullDailyForecastEntityList(): Flow<List<DailyForecastEntity>> {
        return forecastDao.getFullDailyForecastEntityList()
    }

}