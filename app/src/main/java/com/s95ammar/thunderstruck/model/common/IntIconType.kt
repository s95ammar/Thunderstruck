package com.s95ammar.thunderstruck.model.common

import androidx.annotation.IntDef

@IntDef(
    IntIconType.SUNNY,
    IntIconType.MOSTLY_SUNNY,
    IntIconType.PARTLY_SUNNY,
    IntIconType.INTERMITTENT_CLOUDS_DAY,
    IntIconType.HAZY_SUNSHINE,
    IntIconType.MOSTLY_CLOUDY_DAY,
    IntIconType.CLOUDY,
    IntIconType.DREARY,
    IntIconType.FOG,
    IntIconType.SHOWERS,
    IntIconType.MOSTLY_CLOUDY_W_SHOWERS_DAY,
    IntIconType.PARTLY_SUNNY_W_SHOWERS,
    IntIconType.T_STORMS,
    IntIconType.MOSTLY_CLOUDY_W_T_STORMS_DAY,
    IntIconType.PARTLY_SUNNY_W_T_STORMS,
    IntIconType.RAIN,
    IntIconType.FLURRIES,
    IntIconType.MOSTLY_CLOUDY_W_FLURRIES_DAY,
    IntIconType.PARTLY_SUNNY_W_FLURRIES,
    IntIconType.SNOW,
    IntIconType.MOSTLY_CLOUDY_W_SNOW_DAY,
    IntIconType.ICE,
    IntIconType.SLEET,
    IntIconType.FREEZING_RAIN,
    IntIconType.RAIN_AND_SNOW,
    IntIconType.HOT,
    IntIconType.COLD,
    IntIconType.WINDY,
    IntIconType.CLEAR,
    IntIconType.MOSTLY_CLEAR,
    IntIconType.PARTLY_CLOUDY,
    IntIconType.INTERMITTENT_CLOUDS_NIGHT,
    IntIconType.HAZY_MOONLIGHT,
    IntIconType.MOSTLY_CLOUDY_NIGHT,
    IntIconType.PARTLY_CLOUDY_W_SHOWERS,
    IntIconType.MOSTLY_CLOUDY_W_SHOWERS_NIGHT,
    IntIconType.PARTLY_CLOUDY_W_T_STORMS,
    IntIconType.MOSTLY_CLOUDY_W_T_STORMS_NIGHT,
    IntIconType.MOSTLY_CLOUDY_W_FLURRIES_NIGHT,
    IntIconType.MOSTLY_CLOUDY_W_SNOW_NIGHT
)
@Retention(AnnotationRetention.SOURCE)
annotation class IntIconType {
    companion object {
        const val SUNNY = 1
        const val MOSTLY_SUNNY = 2
        const val PARTLY_SUNNY = 3
        const val INTERMITTENT_CLOUDS_DAY = 4
        const val HAZY_SUNSHINE = 5
        const val MOSTLY_CLOUDY_DAY = 6
        const val CLOUDY = 7
        const val DREARY = 8
        const val FOG = 11
        const val SHOWERS = 12
        const val MOSTLY_CLOUDY_W_SHOWERS_DAY = 13
        const val PARTLY_SUNNY_W_SHOWERS = 14
        const val T_STORMS = 15
        const val MOSTLY_CLOUDY_W_T_STORMS_DAY = 16
        const val PARTLY_SUNNY_W_T_STORMS = 17
        const val RAIN = 18
        const val FLURRIES = 19
        const val MOSTLY_CLOUDY_W_FLURRIES_DAY = 20
        const val PARTLY_SUNNY_W_FLURRIES = 21
        const val SNOW = 22
        const val MOSTLY_CLOUDY_W_SNOW_DAY = 23
        const val ICE = 24
        const val SLEET = 25
        const val FREEZING_RAIN = 26
        const val RAIN_AND_SNOW = 29
        const val HOT = 30
        const val COLD = 31
        const val WINDY = 32
        const val CLEAR = 33
        const val MOSTLY_CLEAR = 34
        const val PARTLY_CLOUDY = 35
        const val INTERMITTENT_CLOUDS_NIGHT = 36
        const val HAZY_MOONLIGHT = 37
        const val MOSTLY_CLOUDY_NIGHT = 38
        const val PARTLY_CLOUDY_W_SHOWERS = 39
        const val MOSTLY_CLOUDY_W_SHOWERS_NIGHT = 40
        const val PARTLY_CLOUDY_W_T_STORMS = 41
        const val MOSTLY_CLOUDY_W_T_STORMS_NIGHT = 42
        const val MOSTLY_CLOUDY_W_FLURRIES_NIGHT = 43
        const val MOSTLY_CLOUDY_W_SNOW_NIGHT = 44
    }
}
