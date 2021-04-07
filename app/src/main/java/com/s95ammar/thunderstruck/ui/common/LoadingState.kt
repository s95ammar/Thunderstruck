package com.s95ammar.thunderstruck.ui.common

sealed class LoadingState {
    object Loading: LoadingState()
    object Success: LoadingState()
    class Error(val throwable: Throwable): LoadingState()
}