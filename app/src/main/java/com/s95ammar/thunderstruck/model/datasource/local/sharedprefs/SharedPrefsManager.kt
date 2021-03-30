package com.s95ammar.thunderstruck.model.datasource.local.sharedprefs

interface SharedPrefsManager {
    fun saveLocationKey(locationKey: String)
    fun loadLocationKey(): String?
}