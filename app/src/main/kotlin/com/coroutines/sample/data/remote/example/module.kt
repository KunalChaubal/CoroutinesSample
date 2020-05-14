package com.coroutines.sample.data.remote.example

import com.coroutines.sample.data.remote.createWebService
import com.coroutines.sample.data.remote.provideRetrofit
import org.koin.dsl.module


val fetchDetailsRemoteModule = module {

    single {
        provideRetrofit(
            get(),
            "https://dog.ceo"
        )
    }

    single { FetchDetailsRepo(get()) }

    factory {
        createWebService<FetchDetailsAPI>(
            get()
        )
    }
}