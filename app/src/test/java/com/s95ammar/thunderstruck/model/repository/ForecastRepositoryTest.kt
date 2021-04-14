package com.s95ammar.thunderstruck.model.repository

import com.google.common.truth.Truth.assertThat
import com.s95ammar.thunderstruck.MainCoroutineRule
import com.s95ammar.thunderstruck.model.datasource.Resource
import com.s95ammar.thunderstruck.model.datasource.local.FakeLocalDataSource
import com.s95ammar.thunderstruck.model.datasource.remote.FakeRemoteDataSource
import com.s95ammar.thunderstruck.util.TestDispatcherProvider
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ForecastRepositoryTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var repository: ForecastRepository

    private lateinit var lds: FakeLocalDataSource
    private lateinit var rds: FakeRemoteDataSource

    @Before
    fun setUp() {
        lds = spyk(FakeLocalDataSource())
        rds = spyk(FakeRemoteDataSource())
        repository = ForecastRepository(lds, rds, TestDispatcherProvider)
    }

    @Test
    fun `getFiveDayForecastFlow() db data is fresh, forceUpdate = false, loads from LDS only when collected`() {
        mainCoroutineRule.runBlockingTest {
            lds.setDailyForecastEntityListFresh()
            val forceUpdate = false

            val lastEmittedResource = repository.getFiveDayForecastFlow("key", forceUpdate).toList().last()

            verify(exactly = 1) { lds.getFullDailyForecastEntityListFlow() }
            coVerify(exactly = 0) { rds.getFiveDayForecast(any()) }
            assertThat(lastEmittedResource).isInstanceOf(Resource.Success::class.java)
            assertThat(lastEmittedResource.data).isEqualTo(lds.dailyForecastEntityList)
        }
    }

    @Test
    fun `getFiveDayForecastFlow() db data is outdated, forceUpdate = false, updates cache then returns fresh data from LDS, when collected`() {
        mainCoroutineRule.runBlockingTest {
            lds.setDailyForecastEntityListOutdated()
            val dbDataBeforeCall = lds.dailyForecastEntityList.map { it.copy() }
            val forceUpdate = false

            val lastEmittedResource = repository.getFiveDayForecastFlow("key", forceUpdate).toList().last()

            coVerifyOrder {
                lds.getFullDailyForecastEntityListFlow()
                rds.getFiveDayForecast(any())
                lds.getFullDailyForecastEntityListFlow()
            }
            assertThat(lastEmittedResource).isInstanceOf(Resource.Success::class.java)
            assertThat(lds.dailyForecastEntityList).isNotEqualTo(dbDataBeforeCall)
            assertThat(lastEmittedResource.data).isEqualTo(lds.dailyForecastEntityList)
        }
    }

    @Test
    fun `getFiveDayForecastFlow() no db data, forceUpdate = false, updates cache then returns fresh data from LDS, when collected`() {
        mainCoroutineRule.runBlockingTest {
            lds.setDailyForecastEntityListEmpty()
            val forceUpdate = false

            val lastEmittedResource = repository.getFiveDayForecastFlow("key", forceUpdate).toList().last()

            coVerifyOrder {
                lds.getFullDailyForecastEntityListFlow()
                rds.getFiveDayForecast(any())
                lds.getFullDailyForecastEntityListFlow()
            }
            assertThat(lastEmittedResource).isInstanceOf(Resource.Success::class.java)
            assertThat(lastEmittedResource.data).isNotEmpty()
            assertThat(lastEmittedResource.data).isEqualTo(lds.dailyForecastEntityList)
        }
    }

    @Test
    fun `getFiveDayForecastFlow() db data is fresh, forceUpdate = true, updates cache then returns fresher data from LDS, when collected`() {
        mainCoroutineRule.runBlockingTest {
            lds.setDailyForecastEntityListFresh()
            val dbDataBeforeCall = lds.dailyForecastEntityList.map { it.copy() }
            val forceUpdate = true

            val lastEmittedResource = repository.getFiveDayForecastFlow("key", forceUpdate).toList().last()

            coVerifyOrder {
                lds.getFullDailyForecastEntityListFlow()
                rds.getFiveDayForecast(any())
                lds.getFullDailyForecastEntityListFlow()
            }
            assertThat(lastEmittedResource).isInstanceOf(Resource.Success::class.java)
            assertThat(lds.dailyForecastEntityList).isNotEqualTo(dbDataBeforeCall)
            assertThat(lastEmittedResource.data).isEqualTo(lds.dailyForecastEntityList)
        }
    }

    @Test
    fun `getFiveDayForecastFlow() fetching fails, returns data from LDS, when collected`() {
        mainCoroutineRule.runBlockingTest {
            rds.shouldReturnError = true
            lds.setDailyForecastEntityListOutdated()
            val dbDataBeforeCall = lds.dailyForecastEntityList.map { it.copy() }
            val forceUpdate = false

            val lastEmittedResource = repository.getFiveDayForecastFlow("key", forceUpdate).toList().last()

            coVerifyOrder {
                lds.getFullDailyForecastEntityListFlow()
                rds.getFiveDayForecast(any())
            }
            assertThat(lastEmittedResource).isInstanceOf(Resource.Error::class.java)
            assertThat(lastEmittedResource.data).isNotNull()
            assertThat(lds.dailyForecastEntityList).isEqualTo(dbDataBeforeCall)
            assertThat(lastEmittedResource.data).isEqualTo(lds.dailyForecastEntityList)
        }
    }

    @Test
    fun `getCitySearchResultsFlow() fetching succeeds, returns Resource Success, when collected`() {
        mainCoroutineRule.runBlockingTest {
            val lastEmittedResource = repository.getCitySearchResultsFlow("bla").toList().last()

            assertThat(lastEmittedResource).isInstanceOf(Resource.Success::class.java)
            assertThat(lastEmittedResource.data).isNotNull()
        }
    }

    @Test
    fun `getCitySearchResultsFlow() fetching fails, returns Resource Error, when collected`() {
        mainCoroutineRule.runBlockingTest {
            rds.shouldReturnError = true

            val lastEmittedResource = repository.getCitySearchResultsFlow("bla").toList().last()

            assertThat(lastEmittedResource).isInstanceOf(Resource.Error::class.java)
            assertThat(lastEmittedResource.throwable).isNotNull()
        }
    }
}
