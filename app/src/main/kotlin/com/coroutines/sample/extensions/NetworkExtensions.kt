package com.coroutines.sample.extensions

import com.coroutines.sample.data.remote.model.Resource
import com.coroutines.sample.data.remote.exception.AppException

suspend fun <T> handleException(apiCall: suspend () -> T): Resource<T> {
    return try {
        Resource.success(apiCall.invoke())
    } catch (throwable: Throwable) {
        Resource.error(AppException(throwable))
    }
}