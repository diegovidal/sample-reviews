package com.dvidal.samplereviews.core.di.component

import com.dvidal.samplereviews.core.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

/**
 * @author diegovidal on 2020-02-14.
 */
@Singleton
@Component(modules = [
    ApplicationModule::class
])
interface AppComponent {

}