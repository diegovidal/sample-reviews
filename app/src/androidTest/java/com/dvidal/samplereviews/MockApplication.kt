package com.dvidal.samplereviews

import android.app.Application
import com.dvidal.samplereviews.core.di.component.SeiLa
import com.dvidal.samplereviews.core.di.module.ApplicationModule
import com.dvidal.samplereviews.di.DaggerAppTestComponent

/**
 * @author diegovidal on 2020-02-17.
 */
class MockApplication: Application(), IMyApplication {

    override val appComponent: SeiLa by lazy {
        DaggerAppTestComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }
}