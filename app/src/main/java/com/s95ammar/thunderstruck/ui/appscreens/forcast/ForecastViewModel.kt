package com.s95ammar.thunderstruck.ui.appscreens.forcast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s95ammar.thunderstruck.model.datasource.Resource
import com.s95ammar.thunderstruck.model.repository.ForecastRepository
import com.s95ammar.thunderstruck.ui.appscreens.forcast.data.DailyForecast
import com.s95ammar.thunderstruck.ui.common.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.s95ammar.thunderstruck.ui.appscreens.forcast.data.ForecastUiEvent as UiEvent

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val repository: ForecastRepository
) : ViewModel() {

    private val _dailyForecastList = MutableLiveData<List<DailyForecast>>()
    private val eventChannel = Channel<UiEvent>()

    val dailyForecastList: LiveData<List<DailyForecast>> = _dailyForecastList
    val eventFlow = eventChannel.receiveAsFlow()

    init {
        loadDailyForecastList()
    }

    private fun loadDailyForecastList() = viewModelScope.launch {
        repository.getFiveDayForecast()
            .collect { entityListResource ->
                when (entityListResource) {
                    is Resource.Error -> eventChannel.send(UiEvent.DisplayLoadingState(LoadingState.Error(entityListResource.error)))
                    is Resource.Loading -> eventChannel.send(UiEvent.DisplayLoadingState(LoadingState.Loading))
                    is Resource.Success -> {
                        eventChannel.send(UiEvent.DisplayLoadingState(LoadingState.Success))
                        _dailyForecastList.value = entityListResource.data.map { DailyForecast.EntityMapper.fromEntity(it) }
                    }
                }
            }
    }

}