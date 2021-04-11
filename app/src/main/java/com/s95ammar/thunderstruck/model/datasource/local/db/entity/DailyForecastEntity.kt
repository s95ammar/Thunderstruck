package com.s95ammar.thunderstruck.model.datasource.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.s95ammar.thunderstruck.model.common.IntIconType
import com.s95ammar.thunderstruck.model.datasource.local.db.ThunderstruckDbConfig
import com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.dto.ForecastDto
import com.s95ammar.thunderstruck.util.MILLIS_IN_SECOND
import kotlin.math.roundToInt

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

    object DtoMapper {
        fun toEntity(dailyForecastDto: ForecastDto.DailyForecastDto): DailyForecastEntity? {
            return DailyForecastEntity(
                dayTimestampUnixMs = dailyForecastDto.epochDateSec?.toLong()?.times(MILLIS_IN_SECOND) ?: return null,
                dayIconType = dailyForecastDto.day?.icon ?: return null,
                nightIconType = dailyForecastDto.night?.icon ?: return null,
                minTemperature = dailyForecastDto.temperature?.minimum?.value?.roundToInt() ?: return null,
                maxTemperature = dailyForecastDto.temperature.maximum?.value?.roundToInt() ?: return null,
                temperatureUnit = dailyForecastDto.temperature.maximum.unit ?: return null,
                createdTimestampUnixMs = System.currentTimeMillis()
            )
        }
    }
}