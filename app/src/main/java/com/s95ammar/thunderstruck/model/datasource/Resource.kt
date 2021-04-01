package com.s95ammar.thunderstruck.model.datasource

sealed class Resource<T>(val data: T? = null, val error: Throwable? = null) {

    class Success<T>(data: T, error: Throwable? = null) : Resource<T>(data, error)

    class Loading<T>(data: T? = null, error: Throwable? = null) : Resource<T>(data, error)

    class Error<T>(error: Throwable, data: T? = null) : Resource<T>(data, error)

    override fun toString(): String {
        return super.toString() + "[data = $data, error = ${error?.localizedMessage}]"
    }
}