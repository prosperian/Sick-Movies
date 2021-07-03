package com.dip.sickmovies.api

import java.util.*

class Resource<T> constructor(
    val status: Status,
    var data: T? = null,
    val message: String? = null,
    val date: Long? = null
) {


    companion object {

        fun <T> success(data: T?, date: Long?): Resource<T> {
            return Resource(status = Status.SUCCESS, data = data, date = date)
        }

        fun <T> error(msg: String?, data: T?, date: Long?): Resource<T> {
            return Resource(status = Status.ERROR, data = data, message = msg, date = date)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(status = Status.LOADING, data = data)
        }

        fun <T> cached(data: T?, date: Long?): Resource<T> {
            return Resource(status = Status.CACHED, data = data, date = date)
        }

    }

    enum class Status {
        SUCCESS, ERROR, LOADING, CACHED
    }

}