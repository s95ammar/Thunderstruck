package com.s95ammar.thunderstruck.ui.appscreens.forcast

import androidx.lifecycle.*
import com.s95ammar.thunderstruck.model.datasource.Resource
import com.s95ammar.thunderstruck.model.datasource.local.db.entity.DailyForecastEntity
import com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.isDataFresh
import com.s95ammar.thunderstruck.model.repository.ForecastRepository
import com.s95ammar.thunderstruck.ui.appscreens.forcast.data.DailyForecast
import com.s95ammar.thunderstruck.ui.appscreens.forcast.data.DataFreshness
import com.s95ammar.thunderstruck.ui.appscreens.location.data.LocationInfo
import com.s95ammar.thunderstruck.ui.common.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.s95ammar.thunderstruck.ui.appscreens.forcast.data.ForecastUiEvent as UiEvent

@HiltViewModel
class DailyForecastViewModel @Inject constructor(
    private val repository: ForecastRepository
) : ViewModel() {

    private val _dailyForecastList = MutableLiveData<List<DailyForecast>>()
    private val _locationInfo = MutableLiveData<LocationInfo?>()
    private val _dataFreshness = MutableLiveData<DataFreshness?>()
    private val eventChannel = Channel<UiEvent>()

    val dailyForecastList: LiveData<List<DailyForecast>> = _dailyForecastList
    val locationInfo: LiveData<LocationInfo?> = _locationInfo.distinctUntilChanged()
    val dataStatus: LiveData<DataFreshness?> = _dataFreshness
    val eventFlow = eventChannel.receiveAsFlow()

    init {
        loadForecastOrSelectLocation()
    }

    fun onRefresh() {
        loadForecastOrSelectLocation(forceUpdate = true)
    }

    fun onSelectLocation() = viewModelScope.launch {
        eventChannel.send(UiEvent.NavigateToSearchScreen)
    }

    fun onLocationSelected(locationInfo: LocationInfo) {
        repository.saveLocationInfo(locationInfo)
        _locationInfo.value = locationInfo
        loadDailyForecastList(locationInfo.key, forceUpdate = true)
    }

    private fun loadForecastOrSelectLocation(forceUpdate: Boolean = false) {
        val locationInfo = repository.getLocationInfo()
        _locationInfo.value = locationInfo

        when (locationInfo) {
            null -> viewModelScope.launch { eventChannel.send(UiEvent.NavigateToSearchScreen) }
            else -> loadDailyForecastList(locationInfo.key, forceUpdate)
        }
    }

    private fun loadDailyForecastList(locationKey: String, forceUpdate: Boolean = false) = viewModelScope.launch {
        repository.getFiveDayForecast(locationKey, forceUpdate)
            .collect { entityListResource ->

                fun getDataFreshness(): DataFreshness? {
                    return entityListResource.data?.minOfOrNull(DailyForecastEntity::createdTimestampUnixMs)?.let { lastUpdatedTimestampUnixMs ->
                        DataFreshness(
                            isDataFresh = isDataFresh(lastUpdatedTimestampUnixMs),
                            lastUpdatedTimestampUnixMs = lastUpdatedTimestampUnixMs
                        )
                    }
                }

                when (entityListResource) {
                    is Resource.Loading -> {
                        eventChannel.send(UiEvent.DisplayLoadingState(LoadingState.Loading))
                        _dailyForecastList.value = entityListResource.data?.map { DailyForecast.EntityMapper.fromEntity(it) }.orEmpty()
                    }
                    is Resource.Error -> {
                        eventChannel.send(UiEvent.DisplayLoadingState(LoadingState.Error(entityListResource.throwable)))
                        _dailyForecastList.value = entityListResource.data?.map { DailyForecast.EntityMapper.fromEntity(it) }.orEmpty()
                        _dataFreshness.value = getDataFreshness()
                    }
                    is Resource.Success -> {
                        eventChannel.send(UiEvent.DisplayLoadingState(LoadingState.Success))
                        _dailyForecastList.value = entityListResource.data.map { DailyForecast.EntityMapper.fromEntity(it) }
                        _dataFreshness.value = getDataFreshness()
                    }
                }
            }
    }

}