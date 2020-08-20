package com.example.survey

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class Survey: Application() {

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            return Timber.plant(Timber.DebugTree())
        }
    }
}