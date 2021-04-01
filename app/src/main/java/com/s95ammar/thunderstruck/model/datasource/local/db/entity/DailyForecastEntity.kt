package com.s95ammar.thunderstruck.model.datasource.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.s95ammar.thunderstruck.model.common.IntIconType
import com.s95ammar.thunderstruck.model.datasource.local.db.ThunderstruckDbConfig

@Entity(tableName = ThunderstruckDbConfig.TABLE_NAME_DAILY_FORECAST)
data class DailyForecastEntity(
    val dayTimestampUnixMs: Long,
    @IntIconType val dayIconType: Int,
    @IntIconType val nightIconType: Int,
    val minTemperature: Int,
    val maxTemperature: Int,
    val temperatureUnit: String,
    val createdTimestampUnixMs: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}