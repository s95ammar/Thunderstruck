package com.s95ammar.thunderstruck.model.datasource.local

import com.s95ammar.thunderstruck.model.FakeData
import com.s95ammar.thunderstruck.model.datasource.local.db.entity.DailyForecastEntity
import com.s95ammar.thunderstruck.ui.appscreens.location.data.LocationInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeLocalDataSource : LocalDataSource {

    private val _dailyForecastEntityList = mutableListOf<DailyForecastEntity>()
    private var locationInfo: LocationInfo? = FakeData.dummyLocationInfo

    val dailyForecastEntityList: List<DailyForecastEntity> = _dailyForecastEntityList

    fun setDailyForecastEntityListEmpty() {
        _dailyForecastEntityList.clear()
    }

    fun setDailyForecastEntityListFresh() {
        _dailyForecastEntityList.clear()
        _dailyForecastEntityList.addAll(FakeData.freshDailyForecastEntityList)
    }

    fun setDailyForecastEntityListOutdated() {
        _dailyForecastEntityList.clear()
        _dailyForecastEntityList.addAll(FakeData.outdatedDailyForecastEntityList)
    }

    override suspend fun deleteAllAndInsert(forecastEntityList: List<DailyForecastEntity>) {
        _dailyForecastEntityList.clear()
        _dailyForecastEntityList.addAll(forecastEntityList)
    }

    override fun getFullDailyForecastEntityListFlow(): Flow<List<DailyForecastEntity>> {
        return flowOf(_dailyForecastEntityList)
    }

    override fun saveLocationInfo(locationInfo: LocationInfo) {
        this.locationInfo = locationInfo
    }

    override fun getLocationInfo(): LocationInfo? {
        return locationInfo
    }
}
