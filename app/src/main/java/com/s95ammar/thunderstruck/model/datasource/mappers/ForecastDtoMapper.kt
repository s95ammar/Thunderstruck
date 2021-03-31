package com.s95ammar.thunderstruck.model.datasource.mappers

import com.s95ammar.thunderstruck.model.datasource.local.db.entity.DailyForecastEntity
import com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.dto.ForecastDto
import kotlin.math.roundToInt

object ForecastDtoMapper {
    fun toEntity(dailyForecastDto: ForecastDto.DailyForecastDto): DailyForecastEntity? {
        return DailyForecastEntity(
            timestampUnixMs = dailyForecastDto.epochDate?.toLong() ?: return null,
            dayIconType = dailyForecastDto.day?.icon ?: return null,
            nightIconType = dailyForecastDto.night?.icon ?: return null,
            minTemperature = dailyForecastDto.temperature?.minimum?.value?.roundToInt() ?: return null,
            maxTemperature = dailyForecastDto.temperature.maximum?.value?.roundToInt() ?: return null,
            temperatureUnit = dailyForecastDto.temperature.maximum.unit ?: return null
        )
    }
}