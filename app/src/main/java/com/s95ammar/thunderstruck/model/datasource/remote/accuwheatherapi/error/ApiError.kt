package com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.error

open class ApiError(val responseHttpCode: Int, val errorMsg: String) : Throwable()