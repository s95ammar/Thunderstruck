package com.s95ammar.thunderstruck.model.datasource.local.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.s95ammar.thunderstruck.model.FakeData
import com.s95ammar.thunderstruck.model.datasource.local.db.ThunderstruckDb
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ForecastDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: ThunderstruckDb
    private lateinit var dao: ForecastDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), ThunderstruckDb::class.java)
            .allowMainThreadQueries()
            .build()

        dao = db.forecastDao
    }

    @Test
    fun `insert and read operations`() = runBlockingTest {
        val forecastEntityList = FakeData.fiveDailyForecastEntityList

        dao.insert(forecastEntityList)
        val fullDailyForecastEntityList = dao.getFullDailyForecastEntityList().first()
        assertThat(fullDailyForecastEntityList).containsAtLeastElementsIn(forecastEntityList)
    }

    @Test
    fun `delete all operation`() = runBlockingTest {
        val forecastEntityList = FakeData.fiveDailyForecastEntityList
        dao.insert(forecastEntityList)

        dao.deleteAllDailyForecasts()

        val fullDailyForecastEntityList = dao.getFullDailyForecastEntityList().first()
        assertThat(fullDailyForecastEntityList).isEmpty()
    }

    @After
    fun destroy() {
        db.close()
    }
}