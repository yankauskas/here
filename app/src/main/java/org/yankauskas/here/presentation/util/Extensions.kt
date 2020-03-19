package org.yankauskas.here.presentation.util

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.map
import com.google.android.gms.maps.model.LatLng
import org.yankauskas.here.R
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

fun showError(context: Context, retrofitException: RetrofitException) {
    val alertDialog = AlertDialog.Builder(context).create()
    alertDialog.setTitle(R.string.error)
    alertDialog.setMessage(when (retrofitException) {
        is RetrofitException.NetworkRetrofitException -> context.getString(R.string.internet_error)
        is RetrofitException.HttpRetrofitException -> retrofitException.responseCode.toString()
        is RetrofitException.UnexpectedRetrofitException -> retrofitException.exception.message
    })
    alertDialog.setIcon(android.R.drawable.ic_delete)
    alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, context.getString(R.string.ok)) { _, _ -> alertDialog.cancel() }
    alertDialog.show()
}