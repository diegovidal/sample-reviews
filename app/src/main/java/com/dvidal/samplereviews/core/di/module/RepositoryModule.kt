package com.dvidal.samplereviews.core.di.module

import com.dvidal.samplereviews.core.datasource.local.AppDatabase
import com.dvidal.samplereviews.core.datasource.remote.NetworkHandler
import com.dvidal.samplereviews.core.datasource.remote.RemoteApi
import com.dvidal.samplereviews.features.list.data.local.config.ConfigLocalDataSource
import com.dvidal.samplereviews.features.list.data.local.config.ConfigLocalDataSourceImpl
import com.dvidal.samplereviews.features.list.data.local.reviews.ReviewsLocalDataSource
import com.dvidal.samplereviews.features.list.data.local.reviews.ReviewsLocalDataSourceImpl
import com.dvidal.samplereviews.features.list.data.remote.ReviewsRemoteDataSource
import com.dvidal.samplereviews.features.list.data.remote.ReviewsRemoteDataSourceImpl
import com.dvidal.samplereviews.features.list.domain.ReviewsRepository
import com.dvidal.samplereviews.features.list.domain.ReviewsRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author diegovidal on 2020-02-16.
 */

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideReviewsLocalDataSource(appDatabase: AppDatabase): ReviewsLocalDataSource {
        return ReviewsLocalDataSourceImpl(appDatabase)
    }

    @Singleton
    @Provides
    fun provideReviewsRemotelDataSource(
        remoteApi: RemoteApi,
        networkHandler: NetworkHandler
    ): ReviewsRemoteDataSource {
        return ReviewsRemoteDataSourceImpl(remoteApi, networkHandler)
    }

    @Singleton
    @Provides
    fun provideConfigLocalDataSource(appDatabase: AppDatabase): ConfigLocalDataSource {
        return ConfigLocalDataSourceImpl(appDatabase)
    }

    @Singleton
    @Provides
    fun provideReviewsRepository(
        reviewsLocalDataSource: ReviewsLocalDataSource,
        reviewsRemoteDataSource: ReviewsRemoteDataSource,
        configLocalDataSource: ConfigLocalDataSource
    ): ReviewsRepository {
        return ReviewsRepositoryImpl(
            reviewsLocalDataSource, reviewsRemoteDataSource, configLocalDataSource
        )
    }
}