package com.s95ammar.thunderstruck.model

import com.s95ammar.thunderstruck.model.common.IntIconType
import com.s95ammar.thunderstruck.model.datasource.local.db.entity.DailyForecastEntity
import com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.dto.ForecastDto
import com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.dto.LocationInfoDto
import com.s95ammar.thunderstruck.ui.appscreens.location.data.LocationInfo
import com.s95ammar.thunderstruck.util.MILLIS_IN_SECOND
import java.util.Calendar

object FakeData {

    val remoteDailyForecastResponse = ForecastDto(
        listOf(
            ForecastDto.DailyForecastDto(
                day = ForecastDto.Day(IntIconType.CLEAR),
                epochDateSec = (
                    Calendar.getInstance().apply { set(Calendar.DAY_OF_WEEK, Calendar.MONDAY) }.timeInMillis / MILLIS_IN_SECOND
                    ).toInt(),
                night = ForecastDto.Night(IntIconType.CLOUDY),
                temperature = ForecastDto.Temperature(
                    ForecastDto.Maximum(10.0, "C"),
                    ForecastDto.Minimum(10.0, "C"),
                )
            ),
            ForecastDto.DailyForecastDto(
                day = ForecastDto.Day(IntIconType.CLEAR),
                epochDateSec = (
                    Calendar.getInstance().apply { set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY) }.timeInMillis / MILLIS_IN_SECOND
                    ).toInt(),
                night = ForecastDto.Night(IntIconType.CLOUDY),
                temperature = ForecastDto.Temperature(
                    ForecastDto.Maximum(10.0, "C"),
                    ForecastDto.Minimum(10.0, "C"),
                )
            ),
            ForecastDto.DailyForecastDto(
                day = ForecastDto.Day(IntIconType.CLEAR),
                epochDateSec = (
                    Calendar.getInstance().apply { set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY) }.timeInMillis / MILLIS_IN_SECOND
                    ).toInt(),
                night = ForecastDto.Night(IntIconType.CLOUDY),
                temperature = ForecastDto.Temperature(
                    ForecastDto.Maximum(10.0, "C"),
                    ForecastDto.Minimum(10.0, "C"),
                )
            ),
            ForecastDto.DailyForecastDto(
                day = ForecastDto.Day(IntIconType.CLEAR),
                epochDateSec = (
                    Calendar.getInstance().apply { set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY) }.timeInMillis / MILLIS_IN_SECOND
                    ).toInt(),
                night = ForecastDto.Night(IntIconType.CLOUDY),
                temperature = ForecastDto.Temperature(
                    ForecastDto.Maximum(10.0, "C"),
                    ForecastDto.Minimum(10.0, "C"),
                )
            ),
            ForecastDto.DailyForecastDto(
                day = ForecastDto.Day(IntIconType.CLEAR),
                epochDateSec = (
                    Calendar.getInstance().apply { set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY) }.timeInMillis / MILLIS_IN_SECOND
                    ).toInt(),
                night = ForecastDto.Night(IntIconType.CLOUDY),
                temperature = ForecastDto.Temperature(
                    ForecastDto.Maximum(10.0, "C"),
                    ForecastDto.Minimum(10.0, "C"),
                )
            ),
        )
    )

    val freshDailyForecastEntityList = remoteDailyForecastResponse.dailyForecasts.orEmpty().mapNotNull {
        DailyForecastEntity.DtoMapper.toEntity(it)
    }
    val outdatedDailyForecastEntityList = remoteDailyForecastResponse.dailyForecasts.orEmpty().mapNotNull {
        DailyForecastEntity.DtoMapper.toEntity(it)
    }.map {
        it.copy(createdTimestampUnixMs = 0)
    }

    val dummyLocationInfoDto = LocationInfoDto(
        key = "key",
        city = "city",
        country = LocationInfoDto.Country("country"),
        administrativeArea = LocationInfoDto.AdministrativeArea("adminArea")
    )
    val dummyLocationInfo = LocationInfo.DtoMapper.fromDto(dummyLocationInfoDto)!!
}
