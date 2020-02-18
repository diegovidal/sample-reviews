package com.dvidal.samplereviews

import android.app.Application
import com.dvidal.samplereviews.core.common.BaseAppComponent
import com.dvidal.samplereviews.core.di.module.ApplicationModule
import com.dvidal.samplereviews.di.DaggerAppTestComponent

/**
 * @author diegovidal on 2020-02-17.
 */
class MockApplication: Application(), BaseApplication {

    override val appComponent: BaseAppComponent by lazy {
        DaggerAppTestComponent
            .builder()
            .build()
    }
}