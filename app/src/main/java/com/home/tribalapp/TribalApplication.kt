package com.home.tribalapp

import android.app.Application
import com.home.tribalapp.BuildConfig.ACCESS_KEY
import com.home.tribalapp.BuildConfig.SECRET_KEY
import com.home.tribalapp.di.Injector

class TribalApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // initializing Injection
        Injector.setup(this)
        // initializing the picker library
        UnsplashPhotoPicker.init(
            this,
            ACCESS_KEY,
            SECRET_KEY
            /* optional page size (number of photos per page) */
        )
            /* .setLoggingEnabled(true) // if you want to see the http requests */
    }
}
