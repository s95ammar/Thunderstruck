package com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.dto

import com.google.gson.annotations.SerializedName

data class LocationInfoDto(
    @SerializedName("Key") val key: String?,
    @SerializedName("EnglishName") val city: String?,
    @SerializedName("Country") val country: Country?,
    @SerializedName("AdministrativeArea") val administrativeArea: AdministrativeArea?
) {
    class Country(
        @SerializedName("EnglishName") val name: String?
    )

    class AdministrativeArea(
        @SerializedName("EnglishName") val name: String?
    )
}
