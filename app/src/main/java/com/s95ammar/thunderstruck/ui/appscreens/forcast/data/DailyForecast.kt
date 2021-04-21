package com.s95ammar.thunderstruck.ui.appscreens.forcast.data

import com.s95ammar.thunderstruck.model.common.IntIconType
import com.s95ammar.thunderstruck.model.datasource.local.db.entity.DailyForecastEntity

data class DailyForecast(
    val timestampUnixMs: Long,
    @IntIconType val dayIconType: Int,
    @IntIconType val nightIconType: Int,
    val minTemperature: Int,
    val maxTemperature: Int,
    val temperatureUnit: String
) {
    object EntityMapper {
        fun fromEntity(dailyForecastEntity: DailyForecastEntity): DailyForecast {
            return DailyForecast(
                timestampUnixMs = dailyForecastEntity.dayTimestampUnixMs,
                dayIconType = dailyForecastEntity.dayIconType,
                nightIconType = dailyForecastEntity.nightIconType,
                minTemperature = dailyForecastEntity.minTemperature,
                maxTemperature = dailyForecastEntity.maxTemperature,
                temperatureUnit = dailyForecastEntity.temperatureUnit
            )
        }
    }
}
