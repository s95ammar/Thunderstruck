package com.s95ammar.thunderstruck.model.datasource.local.sharedprefs

import android.content.SharedPreferences
import com.s95ammar.thunderstruck.model.common.Keys
import javax.inject.Inject

class SharedPrefsManagerImpl @Inject constructor(private val sharedPreferences: SharedPreferences) : SharedPrefsManager {

    companion object {
        const val SHARED_PREFERENCES_NAME = "THUNDERSTRUCK_SHARED_PREFERENCES"
    }

    override fun saveLocationKey(locationKey: String) {
        sharedPreferences.edit().putString(Keys.LOCATION_KEY, locationKey).apply()
    }

    override fun loadLocationKey(): String? {
        return sharedPreferences.getString(Keys.LOCATION_KEY, null)
    }
}