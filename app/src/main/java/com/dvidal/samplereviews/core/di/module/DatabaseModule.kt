package com.dvidal.samplereviews.core.di.module

import android.content.Context
import androidx.room.Room
import com.dvidal.samplereviews.core.datasource.local.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author diegovidal on 2020-02-16.
 */

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase {

        return Room.databaseBuilder(context.applicationContext,
            AppDatabase::class.java, DB_NAME).build()
    }

    companion object {

        private const val DB_NAME = "reviews_db"
    }
}