package com.nedaluof.quotes.app

import android.app.Application
import com.nedaluof.quotes.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by NedaluOf on 8/10/2021.
 */
@HiltAndroidApp
class QuotesApp : Application(){
    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}