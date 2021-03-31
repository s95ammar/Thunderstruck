package com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.dto

import com.google.gson.annotations.SerializedName

class ForecastDto(
    @SerializedName("DailyForecasts") val dailyForecasts: List<DailyForecastDto>?
) {

    data class DailyForecastDto(
        @SerializedName("Date") val date: String?,
        @SerializedName("Day") val day: Day?,
        @SerializedName("EpochDate") val epochDate: Int?,
        @SerializedName("Night") val night: Night?,
        @SerializedName("Temperature") val temperature: Temperature?
    )

    data class Day(
        @SerializedName("Icon") val icon: Int?,
    )

    data class Night(
        @SerializedName("Icon") val icon: Int?,
    )

    data class Temperature(
        @SerializedName("Maximum") val maximum: Maximum?,
        @SerializedName("Minimum") val minimum: Minimum?
    )

    data class Maximum(
        @SerializedName("Value") val value: Double?,
        @SerializedName("Unit") val unit: String?
    )

    data class Minimum(
        @SerializedName("Value") val value: Double?,
        @SerializedName("Unit") val unit: String?
    )

}

