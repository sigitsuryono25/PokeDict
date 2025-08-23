package com.surelabsid.core.data.common

sealed class Resources<T>(var data: T? = null, var message: String? = null) {
    class Success<T>(data: T) : Resources<T>(data)
    class Error<T>(message: String, data: T? = null) : Resources<T>(data, message)
    class Loading<T>() : Resources<T>()
}