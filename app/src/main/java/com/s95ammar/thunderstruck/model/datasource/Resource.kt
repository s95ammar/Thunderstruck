package com.s95ammar.thunderstruck.model.datasource

sealed class Resource<T>(
    open val data: T? = null,
    open val throwable: Throwable? = null
) {

    class Success<T>(data: T, error: Throwable? = null) : Resource<T>(data, error) {
        override val data: T
            get() = super.data ?: throw ResourceException.DataNullInSuccess()
    }

    class Loading<T>(data: T? = null, error: Throwable? = null) : Resource<T>(data, error)

    class Error<T>(error: Throwable, data: T? = null) : Resource<T>(data, error) {
        override val throwable: Throwable
            get() = super.throwable ?: throw ResourceException.ThrowableNullInError()
    }

    override fun toString(): String {
        return super.toString() + "[data = $data, error = ${throwable?.localizedMessage}]"
    }
}

sealed class ResourceException(description: String) : IllegalArgumentException(description) {

    class DataNullInSuccess : ResourceException(ERROR_MSG_DATA_NULL_IN_RESOURCE_SUCCESS) {
        companion object {
            private const val ERROR_MSG_DATA_NULL_IN_RESOURCE_SUCCESS = "Resource is of type Success, but 'data' field is null"
        }
    }

    class ThrowableNullInError : ResourceException(ERROR_MSG_THROWABLE_NULL_IN_RESOURCE_ERROR) {
        companion object {
            private const val ERROR_MSG_THROWABLE_NULL_IN_RESOURCE_ERROR = "Resource is of type Error, but 'error' field is null"
        }
    }

}