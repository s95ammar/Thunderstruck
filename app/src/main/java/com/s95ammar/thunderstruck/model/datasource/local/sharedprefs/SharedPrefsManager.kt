package com.s95ammar.thunderstruck.model.datasource.local.sharedprefs

import com.s95ammar.thunderstruck.ui.appscreens.location.data.LocationInfo

interface SharedPrefsManager {
    fun saveLocationInfo(locationInfo: LocationInfo)
    fun loadLocationInfo(): LocationInfo?
}
