package com.dvidal.samplereviews

import android.app.Application
import com.dvidal.samplereviews.core.di.component.AppComponent
import com.dvidal.samplereviews.core.di.component.DaggerAppComponent
import com.dvidal.samplereviews.core.di.component.SeiLa
import com.dvidal.samplereviews.core.di.module.ApplicationModule
import timber.log.Timber

/**
 * @author diegovidal on 2020-02-14.
 */
class MyApplication: Application(), IMyApplication {

    override val appComponent: SeiLa by lazy {
        DaggerAppComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}