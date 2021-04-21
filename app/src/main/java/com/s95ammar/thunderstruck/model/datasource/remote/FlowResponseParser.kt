package com.s95ammar.thunderstruck.model.datasource.remote

import com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.error.ApiError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response

fun <T> Flow<Response<T>>.parseResponse() = map { response ->
    if (response.isSuccessful) {
        val body = response.body()
        requireNotNull(body)
        return@map body
    } else {
        throw ApiError(response.code(), response.errorBody()?.string().orEmpty())
    }
}
