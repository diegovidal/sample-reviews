package com.dvidal.samplereviews.features.list.data.local.config

import androidx.lifecycle.MutableLiveData
import com.dvidal.samplereviews.core.datasource.local.AppDatabase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * @author diegovidal on 2020-02-15.
 */
class ConfigLocalDataSourceImplTest {

    private val appDatabase = mockk<AppDatabase>()

    lateinit var dataSource: ConfigLocalDataSource

    @Before
    fun setup() {
        dataSource = ConfigLocalDataSourceImpl(appDatabase)
    }

    @Test
    fun `when insert config should call configDao insert config`() = runBlocking {

        val dummyConfigDto = ConfigDto()
        coEvery { appDatabase.configDao().insertConfig(dummyConfigDto) } returns Unit

        dataSource.insertConfig(dummyConfigDto.activityName, dummyConfigDto.numReviews, dummyConfigDto.averageRating)
        coVerify(exactly = 1) { appDatabase.configDao().insertConfig(dummyConfigDto) }
    }

    @Test
    fun `when fetch config should call configDao fetch config`() = runBlocking {

        val dummyConfigDto = ConfigDto()
        coEvery { appDatabase.configDao().fetchConfig() } returns dummyConfigDto

        dataSource.fetchConfig()
        coVerify(exactly = 1) { appDatabase.configDao().fetchConfig() }
    }

    @Test
    fun `when fetch config should call configDao fetch config as live data`() = runBlocking {

        val dummyConfigDto = ConfigDto()
        val dummyLiveData = MutableLiveData(dummyConfigDto)
        every { appDatabase.configDao().fetchConfigAsLiveData() } returns dummyLiveData

        dataSource.fetchConfigAsLiveData()
        coVerify(exactly = 1) { appDatabase.configDao().fetchConfigAsLiveData() }
    }

    @Test
    fun `when increment offset page should call configDao increment offset page`() = runBlocking {

        coEvery { appDatabase.configDao().incrementOffsetPage() } returns Unit

        dataSource.incrementOffsetPage()
        coVerify(exactly = 1) { appDatabase.configDao().incrementOffsetPage() }
    }

    @Test
    fun `when clear config should call configDao clear config`() = runBlocking {

        coEvery { appDatabase.configDao().clearConfig() } returns Unit

        dataSource.clearConfig()
        coVerify(exactly = 1) { appDatabase.configDao().clearConfig() }

    }

    @Test
    fun `when toggle sort by rating should call configDao toggle sort by rating`() = runBlocking {

        coEvery { appDatabase.configDao().toggleSortByRating() } returns Unit

        dataSource.toggleSortByRating()
        coVerify(exactly = 1) { appDatabase.configDao().toggleSortByRating() }
    }
}