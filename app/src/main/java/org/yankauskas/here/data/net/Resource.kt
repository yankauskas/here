package org.yankauskas.here.data.net

/**
 * A generic class that holds a value with its loading status.
 *
 * Resource is usually created by the Repository classes where they return
 * `LiveData<Resource<T>>` to pass back the latest data to the UI with its fetch status.
 */

sealed class Resource<T> {
    class Loading<T> : Resource<T>()
    data class Error<T>(val ex: Throwable) : Resource<T>()
    data class Success<T>(val data: T) : Resource<T>()
}

inline fun <T, Y> Resource<T>.mapSuccess(crossinline transform: (T) -> Y): Resource<Y> =
    when (this) {
        is Resource.Loading -> Resource.Loading()
        is Resource.Error -> Resource.Error(ex)
        is Resource.Success -> Resource.Success(transform(data))
    }

inline fun <T> Resource<T>.doOnSuccess(crossinline doBlock: (T) -> Unit): Resource<T> {
    if (this is Resource.Success) doBlock(data)
    return this
}