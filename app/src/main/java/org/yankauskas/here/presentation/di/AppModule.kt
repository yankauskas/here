package org.yankauskas.here.presentation.di

import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.yankauskas.here.presentation.MainViewModel
import org.yankauskas.here.presentation.manager.LocationManager
import org.yankauskas.here.presentation.manager.LocationManagerImpl

/**
 * Created by Viktor Yankauskas (v.yankauskas@synergetica.net) on 18.03.2020.
 */
val appModule = module {

    // single instance of HelloRepository
    single<LocationManager> { LocationManagerImpl(androidContext()) }

    // MyViewModel ViewModel
    viewModel { MainViewModel(get()) }
}