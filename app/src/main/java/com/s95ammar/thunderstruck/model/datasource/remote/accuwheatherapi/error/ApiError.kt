package com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.error

open class ApiError(val responseHttpCode: Int, errorMsg: String) : Throwable(errorMsg)
