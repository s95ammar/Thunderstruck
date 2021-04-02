package com.s95ammar.thunderstruck.ui.appscreens.forcast.data

import com.s95ammar.thunderstruck.ui.common.LoadingState

sealed class ForecastUiEvent {
    class DisplayLoadingState(val loadingState: LoadingState) : ForecastUiEvent()
}
