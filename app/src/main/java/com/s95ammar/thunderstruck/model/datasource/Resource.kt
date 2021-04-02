package com.s95ammar.thunderstruck.model.datasource

sealed class Resource<T>(
    open val data: T? = null,
    open val error: Throwable? = null
) {

    class Success<T>(data: T, error: Throwable? = null) : Resource<T>(data, error) {
        override val data: T
            get() = super.data ?: throw ResourceException("Resource is of type Success, but 'data' field is null")
    }

    class Loading<T>(data: T? = null, error: Throwable? = null) : Resource<T>(data, error)

    class Error<T>(error: Throwable, data: T? = null) : Resource<T>(data, error) {
        override val error: Throwable
            get() = super.error ?: throw ResourceException("Resource is of type Error, but 'error' field is null")
    }

    override fun toString(): String {
        return super.toString() + "[data = $data, error = ${error?.localizedMessage}]"
    }
}

class ResourceException(description: String) : IllegalArgumentException(description)