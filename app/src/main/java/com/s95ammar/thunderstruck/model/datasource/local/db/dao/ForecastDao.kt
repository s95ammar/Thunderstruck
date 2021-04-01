package com.s95ammar.thunderstruck.model.datasource.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.s95ammar.thunderstruck.model.datasource.local.db.entity.DailyForecastEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg forecastEntity: DailyForecastEntity)

    @Query("SELECT * FROM dailyForecast")
    fun getFullDailyForecastEntityList(): Flow<List<DailyForecastEntity>>

}