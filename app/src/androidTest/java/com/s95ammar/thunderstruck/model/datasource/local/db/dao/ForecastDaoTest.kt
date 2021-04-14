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

    private lateinit var dao: ForecastDao
    private lateinit var db: ThunderstruckDb

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), ThunderstruckDb::class.java)
            .allowMainThreadQueries()
            .build()

        dao = db.forecastDao
    }

    @Test
    fun `insert and read operations`() = runBlockingTest {
        val dataList = FakeData.freshDailyForecastEntityList

        dao.insert(dataList)
        val allDataFromTable = dao.getFullDailyForecastEntityListFlow().first()
        assertThat(allDataFromTable).containsAtLeastElementsIn(dataList)
    }

    @Test
    fun `delete all operation`() = runBlockingTest {
        val dataList = FakeData.freshDailyForecastEntityList
        dao.insert(dataList)

        dao.deleteAllDailyForecasts()

        val allDataFromTable = dao.getFullDailyForecastEntityListFlow().first()
        assertThat(allDataFromTable).isEmpty()
    }

    @After
    fun destroy() {
        db.close()
    }
}