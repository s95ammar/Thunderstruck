package com.s95ammar.thunderstruck.ui.appscreens.forcast

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.s95ammar.thunderstruck.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForecastFragment : Fragment(R.layout.forecast_fragment) {

    private val viewModel: ForecastViewModel by viewModels()

}