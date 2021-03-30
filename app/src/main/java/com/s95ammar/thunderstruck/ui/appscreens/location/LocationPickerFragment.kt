package com.s95ammar.thunderstruck.ui.appscreens.location

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.s95ammar.thunderstruck.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationPickerFragment : Fragment(R.layout.location_picker_fragment) {

    private val viewModel: LocationPickerViewModel by viewModels()

}