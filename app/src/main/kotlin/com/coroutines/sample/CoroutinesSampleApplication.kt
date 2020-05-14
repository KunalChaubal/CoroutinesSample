package com.coroutines.sample

import android.app.Application
import com.coroutines.sample.data.remote.example.fetchDetailsRemoteModule
import com.coroutines.sample.data.remote.networkModule
import com.coroutines.sample.ui.example.fetchDetailsModule
import com.coroutines.sample.ui.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Application class
 */
class CoroutinesSampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(listOf(mainModule, fetchDetailsModule, fetchDetailsRemoteModule,
                networkModule
            ))
        }
    }
}