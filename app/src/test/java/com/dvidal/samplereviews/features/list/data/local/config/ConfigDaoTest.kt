package com.dvidal.samplereviews.features.list.data.local.config

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import com.dvidal.samplereviews.core.datasource.local.AppDatabase
import com.dvidal.samplereviews.utils.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment


/**
 * @author diegovidal on 2020-02-15.
 */

@RunWith(RobolectricTestRunner::class)
class ConfigDaoTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val appDatabase = Room.inMemoryDatabaseBuilder(
        RuntimeEnvironment.systemContext,
        AppDatabase::class.java
    )
        .allowMainThreadQueries()
        .build()

    @After
    fun closeDb() {
        appDatabase.close()
    }

    @Test
    fun `when add config should return config`() = runBlocking {

        val dummyConfigDto = ConfigDto()

        appDatabase.configDao().insertConfig(dummyConfigDto)
        val configDto = appDatabase.configDao().fetchConfig()
        assertEquals(dummyConfigDto, configDto)
    }

    @Test
    fun `when add config should return config as live data`() = runBlocking {

        val dummyConfigDto = ConfigDto()

        appDatabase.configDao().insertConfig(dummyConfigDto)
        val configDto = appDatabase.configDao().fetchConfigAsLiveData()
        assertEquals(dummyConfigDto, configDto.getOrAwaitValue())
    }

    @Test
    fun `when add and clear config should return nothing`() = runBlocking {

        val dummyConfigDto = ConfigDto()

        appDatabase.configDao().insertConfig(dummyConfigDto)
        var configDto = appDatabase.configDao().fetchConfigAsLiveData()
        assertEquals(dummyConfigDto, configDto.getOrAwaitValue())

        appDatabase.configDao().clearConfig()
        configDto = appDatabase.configDao().fetchConfigAsLiveData()
        assertEquals(null, configDto.getOrAwaitValue())
    }

    @Test
    fun `when add and increment two times offsetPage should return 2`() = runBlocking {

        val dummyConfigDto = ConfigDto()

        appDatabase.configDao().insertConfig(dummyConfigDto)
        val configDto = appDatabase.configDao().fetchConfigAsLiveData()
        assertEquals(0, configDto.getOrAwaitValue()?.offsetPage)

        repeat(2) {appDatabase.configDao().incrementOffsetPage()}
        assertEquals(2, configDto.getOrAwaitValue()?.offsetPage)
    }

    @Test
    fun `when toggle sort by rating should return false`() = runBlocking {

        val dummyConfigDto = ConfigDto()

        appDatabase.configDao().insertConfig(dummyConfigDto)
        var configDto = appDatabase.configDao().fetchConfigAsLiveData()
        assertEquals(true, configDto.getOrAwaitValue()?.isDescendingOrderRating)

        appDatabase.configDao().toggleSortByRating()
        configDto = appDatabase.configDao().fetchConfigAsLiveData()
        assertEquals(false, configDto.getOrAwaitValue()?.isDescendingOrderRating)
    }

    @Test
    fun `when fetch config and has nothing should return null`() = runBlocking {

        val configDto = appDatabase.configDao().fetchConfig()
        assertEquals(null, configDto)
    }
}