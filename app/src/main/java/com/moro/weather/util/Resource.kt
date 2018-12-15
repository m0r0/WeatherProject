package com.moro.weather.util

/**
 * Created with Android Studio.
 * User: Vitalii Morozov
 * Date: 12/15/18
 * Time: 7:49 PM
 */
class Resource<E> private constructor(val data: E? = null, val status: Status, val error: String? = null) {
    companion object {
        fun <E> success(data: E) = Resource(data, Status.SUCCESS)

        fun <E> loading(): Resource<E> = Resource(status = Status.LOADING)

        fun <E> error(error: String): Resource<E> = Resource(status = Status.ERROR, error = error)

    }

    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    fun isSuccess() = status == Status.SUCCESS && data != null

    fun isFailure() = status == Status.ERROR
}