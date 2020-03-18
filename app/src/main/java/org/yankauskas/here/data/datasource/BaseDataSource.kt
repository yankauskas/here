package org.yankauskas.here.data.datasource

import retrofit2.Response
import org.yankauskas.here.data.net.Resource
import org.yankauskas.here.data.net.RetrofitException
import java.net.ConnectException

/**
 * Abstract Base Data source class with error handling
 */
abstract class BaseDataSource {

    protected suspend fun <T> getResource(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body() as T
                return Resource.Success(body)
            }
            return Resource.Error(RetrofitException.HttpRetrofitException(response.code()))
        } catch (e: Exception) {
            return if (e is ConnectException)
                Resource.Error(RetrofitException.NetworkRetrofitException(e))
            else
                Resource.Error(RetrofitException.UnexpectedRetrofitException(e))
        }
    }
}

