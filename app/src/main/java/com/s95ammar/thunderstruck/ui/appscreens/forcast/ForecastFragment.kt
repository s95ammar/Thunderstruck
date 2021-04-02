package com.s95ammar.thunderstruck.ui.appscreens.forcast

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.s95ammar.thunderstruck.R
import com.s95ammar.thunderstruck.ui.appscreens.forcast.data.DailyForecast
import com.s95ammar.thunderstruck.ui.common.LoadingState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import com.s95ammar.thunderstruck.ui.appscreens.forcast.data.ForecastUiEvent as UiEvent

@AndroidEntryPoint
class ForecastFragment : Fragment(R.layout.forecast_fragment) {

    private val viewModel: ForecastViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        initObservers()
    }

    private fun setUpView() {
        TODO("Not yet implemented")
    }

    private fun initObservers() {
        viewModel.dailyForecastList.observe(viewLifecycleOwner) { setDailyForecastList(it) }

        lifecycleScope.launchWhenStarted {
            viewModel.eventFlow.collect { handleUiEvent(it) }
        }
    }

    private fun handleUiEvent(uiEvent: UiEvent) {
        when (uiEvent) {
            is UiEvent.DisplayLoadingState -> displayLoadingState(uiEvent.loadingState)
        }
    }

    private fun setDailyForecastList(dailyForecastList: List<DailyForecast>) {
        TODO("Not yet implemented")
    }

    private fun displayLoadingState(loadingState: LoadingState) {
        when (loadingState) {
            is LoadingState.Error -> TODO()
            is LoadingState.Loading -> TODO()
            is LoadingState.Success -> TODO()
        }
    }
}