package com.dvidal.samplereviews.features.list.data.local.config

import androidx.lifecycle.LiveData
import com.dvidal.samplereviews.core.common.EitherResult
import com.dvidal.samplereviews.core.common.catching
import com.dvidal.samplereviews.core.datasource.local.AppDatabase
import com.dvidal.samplereviews.core.datasource.local.LocalConstants
import javax.inject.Inject

/**
 * @author diegovidal on 2020-02-14.
 */
class ConfigLocalDataSourceImpl @Inject constructor(
    private val appDatabase: AppDatabase
): ConfigLocalDataSource {

    override suspend fun insertConfig(configDto: ConfigDto): EitherResult<Unit> {
        configDto.activityName = LocalConstants.ACTIVITY_TITLE
        return catching { appDatabase.configDao().insertConfig(configDto) }
    }

    override suspend fun fetchConfig(): EitherResult<ConfigDto?> {
        return catching { appDatabase.configDao().fetchConfig() }
    }

    override fun fetchConfigAsLiveData(): EitherResult<LiveData<ConfigDto?>> {
        return catching { appDatabase.configDao().fetchConfigAsLiveData() }
    }

    override suspend fun incrementOffsetPage(): EitherResult<Unit> {
        return catching { appDatabase.configDao().incrementOffsetPage() }
    }

    override suspend fun toggleSortByRating(): EitherResult<Unit> {
        return catching { appDatabase.configDao().toggleSortByRating() }
    }

    override suspend fun clearConfig(): EitherResult<Unit> {
        return catching { appDatabase.configDao().clearConfig() }
    }
}