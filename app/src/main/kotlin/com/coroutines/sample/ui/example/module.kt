package com.coroutines.sample.ui.example

import com.coroutines.sample.manager.CoroutinesManager
import com.coroutines.sample.ui.utils.ResourceProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val fetchDetailsModule = module {
    viewModel { FetchDetailsViewModel(get(), get()) }
    single { ResourceProvider(androidApplication()) }
    single { CoroutinesManager() }
}