package com.s95ammar.thunderstruck.ui.common

class ViewBindingUnavailableException : IllegalStateException(ERROR_MSG) {
    companion object {
        const val ERROR_MSG = "viewBinding was accessed from outside of the fragment view's lifecycle"
    }
}
