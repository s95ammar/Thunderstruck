package com.s95ammar.thunderstruck.ui.appscreens.location.data

import android.os.Parcelable
import com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.dto.LocationInfoDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationInfo(
    val key: String,
    val city: String,
    val administrativeArea: String,
    val country: String
) : Parcelable {

    object DtoMapper {
        fun fromDto(dto: LocationInfoDto): LocationInfo? {
            return LocationInfo(
                key = dto.key ?: return null,
                city = dto.city ?: return null,
                administrativeArea = dto.administrativeArea?.name ?: return null,
                country = dto.country?.name ?: return null,
            )
        }
    }

}
