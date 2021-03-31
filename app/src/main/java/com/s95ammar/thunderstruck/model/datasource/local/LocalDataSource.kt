package com.s95ammar.thunderstruck.model.datasource.local

import com.s95ammar.thunderstruck.model.datasource.local.db.dao.ForecastDao
import com.s95ammar.thunderstruck.model.datasource.local.sharedprefs.SharedPrefsManager
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val sharedPrefsManager: SharedPrefsManager,
    private val forecastDao: ForecastDao
) {
}