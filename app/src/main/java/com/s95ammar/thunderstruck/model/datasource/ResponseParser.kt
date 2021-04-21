package com.s95ammar.thunderstruck.model.datasource

import com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.error.ApiError
import com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.error.EmptyResponseBodyError
import retrofit2.Response

@Throws(ApiError::class)
fun <Dto> Response<Dto>.parseResponse(): Dto {
    if (isSuccessful) {
        return body() ?: throw EmptyResponseBodyError(code())
    } else {
        throw ApiError(code(), errorBody()?.string().orEmpty())
    }
}
