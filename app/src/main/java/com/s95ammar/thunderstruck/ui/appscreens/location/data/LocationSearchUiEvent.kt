package com.s95ammar.thunderstruck.ui.appscreens.location.data

import com.s95ammar.thunderstruck.ui.common.LoadingState

sealed class LocationSearchUiEvent {
    class DisplayLoadingState(val loadingState: LoadingState) : LocationSearchUiEvent()
    class SetResult(val locationInfo: LocationInfo) : LocationSearchUiEvent()
    object Exit : LocationSearchUiEvent()
}
