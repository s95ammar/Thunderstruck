package com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.error

class EmptyResponseBodyError(responseCode: Int): ApiError(responseCode, ERROR_MSG) {
    companion object {
        private const val ERROR_MSG = "Empty response body"
    }
}