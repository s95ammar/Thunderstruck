package com.s95ammar.thunderstruck.model.datasource.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.s95ammar.thunderstruck.model.datasource.local.db.dao.ForecastDao
import com.s95ammar.thunderstruck.model.datasource.local.db.entity.DailyForecastEntity

@Database(
    entities = [DailyForecastEntity::class],
    version = ThunderstruckDbConfig.DB_VERSION
)
abstract class ThunderstruckDb : RoomDatabase() {
    abstract val forecastDao: ForecastDao
}
