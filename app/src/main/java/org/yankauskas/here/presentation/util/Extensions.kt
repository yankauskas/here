package org.yankauskas.here.presentation.util

import android.view.View
import androidx.lifecycle.*
import com.google.android.gms.maps.model.LatLng
import org.yankauskas.here.data.net.Resource
import org.yankauskas.here.data.net.RetrofitException
import org.yankauskas.here.data.net.mapSuccess

/**
 * Created by Viktor Yankauskas (v.yankauskas@synergetica.net) on 18.03.2020.
 */

fun LatLng.toHereParam() = "$latitude,$longitude"

fun String.nullIfEmpty(): String? = if (isEmpty()) null else this

inline fun <T, Y> LiveData<Resource<T>>.mapSuccess(crossinline transform: (T) -> Y) = map { it.mapSuccess(transform) }

inline fun <T> LiveData<Resource<T>>.observeResource(lifecycleOwner: LifecycleOwner,
                                                     crossinline loading: () -> Unit = {},
                                                     crossinline error: (RetrofitException) -> Unit = {},
                                                     crossinline success: (T) -> Unit = {}) {
    observe(lifecycleOwner, Observer {
        when (this.value) {
            is Resource.Loading -> loading()
            is Resource.Error -> error((this.value as Resource.Error).ex)
            is Resource.Success -> success((this.value as Resource.Success).data)
        }
    })
}

inline fun <T> LifecycleOwner.observeLiveData(liveData: LiveData<T>, crossinline runBlock: (T) -> Unit) {
    liveData.observe(this, Observer { runBlock(it) })
}

inline fun <T> LifecycleOwner.observeResource(liveData: LiveData<Resource<T>>,
                                                   crossinline loading: () -> Unit = {},
                                                   crossinline error: (RetrofitException) -> Unit = {},
                                                   crossinline success: (T) -> Unit = {}) {
    liveData.observeResource(this, loading,
        error = { error(it) },
        success = { success(it) })
}

fun View.visible() { this.visibility = View.VISIBLE }

fun View.gone() { this.visibility = View.GONE }
