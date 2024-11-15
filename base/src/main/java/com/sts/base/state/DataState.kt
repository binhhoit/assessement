package com.sts.base.state

sealed class DataState<out T> {

    // Data state for loading to show/hide loading progress
    data class Loading<out T>(val isLoading: Boolean = true, val isRefresh: Boolean = false) : DataState<T>()

    // Data state for emitting data to Observer
    data class Success<out T>(val data: T) : DataState<T>()

    data class Failure<out T>(val message: Throwable) : DataState<T>()
}