package com.andresen.library_repositories.util.network


sealed class DataResult<T> {
    sealed class Error : DataResult<Nothing>() {
        abstract val err: Throwable?

        data class NoNetwork(override val err: Throwable?) : Error()
        data class AppError(override val err: Throwable?) : Error()
    }

    sealed class Success<T> : DataResult<T>() {
        abstract val data: T

        data class NetworkSuccess<T>(override val data: T) : Success<T>()
        data class OfflineSuccess<T>(override val data: T) : Success<T>()
    }

    fun getOrNull(): T? = (this as? Success)?.data
}