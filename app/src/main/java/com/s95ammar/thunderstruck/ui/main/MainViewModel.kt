package com.s95ammar.thunderstruck.ui.main

import androidx.lifecycle.ViewModel
import com.s95ammar.thunderstruck.model.repository.ForecastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ForecastRepository
) : ViewModel() {

}
