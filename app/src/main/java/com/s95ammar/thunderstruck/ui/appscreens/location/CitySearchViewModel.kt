package com.s95ammar.thunderstruck.ui.appscreens.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s95ammar.thunderstruck.model.datasource.Resource
import com.s95ammar.thunderstruck.model.repository.ForecastRepository
import com.s95ammar.thunderstruck.ui.appscreens.location.data.LocationInfo
import com.s95ammar.thunderstruck.ui.common.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.s95ammar.thunderstruck.ui.appscreens.location.data.LocationSearchUiEvent as UiEvent

@HiltViewModel
class CitySearchViewModel @Inject constructor(
    private val repository: ForecastRepository
) : ViewModel() {

    private val _searchResults = MutableLiveData<List<LocationInfo>>()
    private val eventChannel = Channel<UiEvent>()

    val searchResults: LiveData<List<LocationInfo>> = _searchResults
    val eventFlow = eventChannel.receiveAsFlow()

    fun onSearchResultClick(position: Int) = viewModelScope.launch {
        _searchResults.value?.get(position)?.let {
            eventChannel.send(UiEvent.SetResult(it))
            eventChannel.send(UiEvent.Exit)
        }
    }

    fun getCitySearchResults(query: String) = viewModelScope.launch {
        if (query.isEmpty()) {
            _searchResults.value = emptyList()
            return@launch
        }

        repository.getCitySearchResultsFlow(query)
            .collect { resourceSearchResultDtoList ->
                when (resourceSearchResultDtoList) {
                    is Resource.Error -> {
                        eventChannel.send(UiEvent.DisplayLoadingState(LoadingState.Error(resourceSearchResultDtoList.throwable)))
                    }
                    is Resource.Loading -> {
                        eventChannel.send(UiEvent.DisplayLoadingState(LoadingState.Loading))
                    }
                    is Resource.Success -> {
                        eventChannel.send(UiEvent.DisplayLoadingState(LoadingState.Success))
                        _searchResults.value = resourceSearchResultDtoList.data.mapNotNull { LocationInfo.DtoMapper.fromDto(it) }
                    }
                }
            }
    }
}
