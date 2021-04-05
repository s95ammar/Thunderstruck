package com.s95ammar.thunderstruck.model.datasource

import com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.error.ApiError
import com.s95ammar.thunderstruck.model.datasource.remote.accuwheatherapi.error.EmptyResponseBodyError
import kotlinx.coroutines.flow.*
import retrofit2.Response

fun <Dto, Entity> networkBoundResource(
    queryFlow: Flow<Entity>,
    fetch: suspend () -> Response<Dto>,
    insert: suspend (Dto) -> Unit,
    shouldFetch: (Entity) -> Boolean = { true }
): Flow<Resource<Entity>> = flow {

    emit(Resource.Loading())

    val entity = queryFlow.first()

    val resultFlow = if (shouldFetch(entity)) {

        emit(Resource.Loading(data = entity))

        try {
            val dto = fetch().parseResponse()
            insert(dto)
            queryFlow.map { Resource.Success(it) }
        } catch (t: Throwable) {
            flowOf(Resource.Error(data = entity, error = t))
        }

    } else {
        flowOf(Resource.Success(entity))
    }

    emitAll(resultFlow)
}


@Throws(ApiError::class)
private fun <Dto> Response<Dto>.parseResponse(): Dto {
    if (isSuccessful) {
        return body() ?: throw EmptyResponseBodyError(code())
    } else {
        throw ApiError(code(), errorBody()?.string().orEmpty())
    }
}