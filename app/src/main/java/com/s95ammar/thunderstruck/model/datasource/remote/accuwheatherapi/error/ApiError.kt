package com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.error

class ApiError(val httpErrorCode: Int, val errorMsg: String) : Throwable()