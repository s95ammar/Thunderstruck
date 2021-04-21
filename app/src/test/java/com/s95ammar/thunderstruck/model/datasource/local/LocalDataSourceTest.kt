package com.s95ammar.thunderstruck.model.datasource.local

import android.app.Application
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.s95ammar.thunderstruck.model.FakeData
import com.s95ammar.thunderstruck.model.datasource.local.db.ThunderstruckDb
import com.s95ammar.thunderstruck.model.datasource.local.db.dao.ForecastDao
import com.s95ammar.thunderstruck.model.datasource.local.sharedprefs.SharedPrefsManager
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.Executors

@RunWith(AndroidJUnit4::class)
class LocalDataSourceTest {

    private lateinit var localDataSource: LocalDataSourceImpl
    private lateinit var db: ThunderstruckDb

    @MockK
    lateinit var dao: ForecastDao

    @MockK
    lateinit var sharedPrefsManager: SharedPrefsManager

    @Before
    fun setUp() {
        val appContext = ApplicationProvider.getApplicationContext<Application>()
        db = Room.inMemoryDatabaseBuilder(appContext, ThunderstruckDb::class.java)
            .allowMainThreadQueries()
            .setTransactionExecutor(Executors.newSingleThreadExecutor())
            .build()

        MockKAnnotations.init(this)
        localDataSource = LocalDataSourceImpl(sharedPrefsManager, db, dao)
    }

    @Test
    fun `deleteAllAndInsert calls both delete and insert on dao and in order`() = runBlocking { // Room transactions don't work with runBlockingTest :/
        val data = FakeData.freshDailyForecastEntityList
        coEvery { dao.deleteAllDailyForecasts() } just Runs
        coEvery { dao.insert(data) } just Runs

        localDataSource.deleteAllAndInsert(data)

        coVerifyOrder {
            dao.deleteAllDailyForecasts()
            dao.insert(data)
        }
    }

    @Test
    fun `getFullDailyForecastEntityList forwards the call to dao and returns the same flow`() {
        val daoReturnValue = flowOf(FakeData.freshDailyForecastEntityList)
        every { dao.getFullDailyForecastEntityListFlow() } returns daoReturnValue

        val actualReturnValue = localDataSource.getFullDailyForecastEntityListFlow()

        assertThat(actualReturnValue).isSameInstanceAs(daoReturnValue)
    }

    @Test
    fun `saveLocationInfo forwards the call to sharedPrefsManager`() {
        val locationInfo = FakeData.dummyLocationInfo
        every { sharedPrefsManager.saveLocationInfo(any()) } just Runs

        localDataSource.saveLocationInfo(locationInfo)

        verify(exactly = 1) { sharedPrefsManager.saveLocationInfo(locationInfo) }
    }

    @Test
    fun `getLocationInfo forwards the call to sharedPrefsManager and returns the same LocationInfo`() {
        val sharedPrefsReturnValue = FakeData.dummyLocationInfo
        every { sharedPrefsManager.loadLocationInfo() } returns sharedPrefsReturnValue

        val actualReturnValue = localDataSource.getLocationInfo()

        assertThat(actualReturnValue).isSameInstanceAs(sharedPrefsReturnValue)
    }

}