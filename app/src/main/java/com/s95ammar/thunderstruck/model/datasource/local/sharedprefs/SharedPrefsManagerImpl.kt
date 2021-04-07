package com.s95ammar.thunderstruck.model.datasource.local.sharedprefs

import android.content.SharedPreferences
import com.google.gson.Gson
import com.s95ammar.thunderstruck.Keys
import com.s95ammar.thunderstruck.ui.appscreens.location.data.LocationInfo
import javax.inject.Inject

class SharedPrefsManagerImpl @Inject constructor(private val sharedPreferences: SharedPreferences) : SharedPrefsManager {

    companion object {
        const val SHARED_PREFERENCES_NAME = "THUNDERSTRUCK_SHARED_PREFERENCES"
    }

    override fun saveLocationInfo(locationInfo: LocationInfo) {
        sharedPreferences.edit().putString(Keys.LOCATION_INFO, Gson().toJson(locationInfo)).apply()
    }

    override fun loadLocationInfo(): LocationInfo? {
        return sharedPreferences.getString(Keys.LOCATION_INFO, null)?.let { json ->
            Gson().fromJson(json, LocationInfo::class.java)
        }
    }
}