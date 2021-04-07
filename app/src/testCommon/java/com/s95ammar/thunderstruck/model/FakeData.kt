package com.s95ammar.thunderstruck.model

import com.s95ammar.thunderstruck.model.common.IntIconType
import com.s95ammar.thunderstruck.model.datasource.local.db.entity.DailyForecastEntity
import com.s95ammar.thunderstruck.ui.appscreens.location.data.LocationInfo
import java.util.*

object FakeData {

    val fiveDailyForecastEntityList = listOf(
        DailyForecastEntity(
            dayTimestampUnixMs = Calendar.getInstance().apply { set(Calendar.DAY_OF_WEEK, Calendar.MONDAY) }.timeInMillis,
            dayIconType = IntIconType.CLEAR,
            nightIconType = IntIconType.CLOUDY,
            minTemperature = -5,
            maxTemperature = 10,
            temperatureUnit = "C",
            createdTimestampUnixMs = System.currentTimeMillis(),
        ),
        DailyForecastEntity(
            dayTimestampUnixMs = Calendar.getInstance().apply { set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY) }.timeInMillis,
            dayIconType = IntIconType.CLEAR,
            nightIconType = IntIconType.CLOUDY,
            minTemperature = -5,
            maxTemperature = 10,
            temperatureUnit = "C",
            createdTimestampUnixMs = System.currentTimeMillis(),
        ),
        DailyForecastEntity(
            dayTimestampUnixMs = Calendar.getInstance().apply { set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY) }.timeInMillis,
            dayIconType = IntIconType.CLEAR,
            nightIconType = IntIconType.CLOUDY,
            minTemperature = -5,
            maxTemperature = 10,
            temperatureUnit = "C",
            createdTimestampUnixMs = System.currentTimeMillis(),
        ),
        DailyForecastEntity(
            dayTimestampUnixMs = Calendar.getInstance().apply { set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY) }.timeInMillis,
            dayIconType = IntIconType.CLEAR,
            nightIconType = IntIconType.CLOUDY,
            minTemperature = -5,
            maxTemperature = 10,
            temperatureUnit = "C",
            createdTimestampUnixMs = System.currentTimeMillis(),
        ),
        DailyForecastEntity(
            dayTimestampUnixMs = Calendar.getInstance().apply { set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY) }.timeInMillis,
            dayIconType = IntIconType.CLEAR,
            nightIconType = IntIconType.CLOUDY,
            minTemperature = -5,
            maxTemperature = 10,
            temperatureUnit = "C",
            createdTimestampUnixMs = System.currentTimeMillis(),
        ),
    )

    val dummyLocationInfo = LocationInfo("key", "city", "adminArea", "country")
}