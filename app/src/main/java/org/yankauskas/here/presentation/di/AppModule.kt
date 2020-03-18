package org.yankauskas.here.presentation.di

import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.yankauskas.here.R
import org.yankauskas.here.data.HereRepository
import org.yankauskas.here.data.datasource.HereWebDataSource
import org.yankauskas.here.data.net.HereApiService
import org.yankauskas.here.presentation.MainViewModel
import org.yankauskas.here.presentation.manager.LocationManager
import org.yankauskas.here.presentation.manager.LocationManagerImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by Viktor Yankauskas (v.yankauskas@synergetica.net) on 18.03.2020.
 */
val appModule = module {

    single<LocationManager> { LocationManagerImpl(androidContext()) }

    single {
        OkHttpClient.Builder()
            .addInterceptor {
                val url = it.request().url.newBuilder()
                    .addQueryParameter("apiKey", androidContext().getString(R.string.here_api_key))
                    .build()

                it.proceed(it.request().newBuilder().url(url).build())
            }
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single<HereApiService> {
        get<Retrofit>().create(HereApiService::class.java)
    }

    single { HereWebDataSource(get()) }

    single { HereRepository(get()) }

    viewModel { MainViewModel(get()) }


}