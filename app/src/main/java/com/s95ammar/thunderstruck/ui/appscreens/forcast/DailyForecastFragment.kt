package com.s95ammar.thunderstruck.ui.appscreens.forcast

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.s95ammar.thunderstruck.Keys
import com.s95ammar.thunderstruck.R
import com.s95ammar.thunderstruck.databinding.DailyForecastFragmentBinding
import com.s95ammar.thunderstruck.ui.appscreens.forcast.adapter.DailyForecastAdapter
import com.s95ammar.thunderstruck.ui.appscreens.forcast.data.DailyForecast
import com.s95ammar.thunderstruck.ui.appscreens.forcast.data.DataFreshness
import com.s95ammar.thunderstruck.ui.appscreens.location.data.LocationInfo
import com.s95ammar.thunderstruck.ui.common.LoadingState
import com.s95ammar.thunderstruck.ui.common.ViewBindingUnavailableException
import com.s95ammar.thunderstruck.util.DateUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.*
import com.s95ammar.thunderstruck.ui.appscreens.forcast.data.ForecastUiEvent as UiEvent

@AndroidEntryPoint
class DailyForecastFragment : Fragment(R.layout.daily_forecast_fragment) {

    private val viewModel: DailyForecastViewModel by viewModels()
    private val adapter by lazy { DailyForecastAdapter() }

    private var _binding: DailyForecastFragmentBinding? = null
    private val binding get() = _binding ?: throw ViewBindingUnavailableException()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = DailyForecastFragmentBinding.bind(view)
        setUpViews()
        initObservers()
    }

    private fun setUpViews() {
        binding.dailyForecastRecyclerView.adapter = adapter
        binding.dailyForecastRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.dailyForecastRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        binding.swipeToRefresh.setOnRefreshListener { viewModel.onRefresh() }
        binding.locationTextView.setOnClickListener { viewModel.onSelectLocation() }
    }

    private fun initObservers() {
        viewModel.dailyForecastList.observe(viewLifecycleOwner) { setDailyForecastList(it) }
        viewModel.dataStatus.observe(viewLifecycleOwner) { setDataStatus(it) }
        viewModel.locationInfo.observe(viewLifecycleOwner) { setLocationInfo(it) }

        lifecycleScope.launchWhenStarted {
            viewModel.eventFlow.collect { handleUiEvent(it) }
        }
    }

    private fun setDailyForecastList(dailyForecastList: List<DailyForecast>) {
        adapter.submitList(dailyForecastList)
    }

    private fun setDataStatus(dataFreshness: DataFreshness?) {
        if (dataFreshness != null) {
            if (dataFreshness.isDataFresh) {
                binding.dataStatusTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check, 0, 0, 0)
                binding.dataStatusTextView.text = getString(R.string.data_status_fresh)
            } else {
                binding.dataStatusTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_warning, 0, 0, 0)
                binding.dataStatusTextView.text = getString(R.string.data_status_outdated)
            }
        }

        binding.lastUpdatedTextView.isGone = (dataFreshness == null)
        binding.lastUpdatedTextView.text = dataFreshness?.lastUpdatedTimestampUnixMs?.let { timestampUnixMs ->
            getString(R.string.format_last_updated, DateUtil.getFullDateFormatted(Date(timestampUnixMs)))
        }
    }

    private fun setLocationInfo(locationInfo: LocationInfo?) {
        binding.locationTextView.text = getString(R.string.current_location, locationInfo?.city ?: getString(R.string.not_selected))
    }

    private fun handleUiEvent(uiEvent: UiEvent) {
        when (uiEvent) {
            is UiEvent.DisplayLoadingState -> displayLoadingState(uiEvent.loadingState)
            is UiEvent.NavigateToSearchScreen -> {
                listenToLocationResult()
                navigateToSearchScreen()
            }
        }
    }

    private fun displayLoadingState(loadingState: LoadingState) {
        when (loadingState) {
            is LoadingState.Error -> {
                val errorMessage = loadingState.throwable.localizedMessage ?: getString(R.string.error_occurred)
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                binding.swipeToRefresh.isRefreshing = false
            }
            is LoadingState.Loading -> binding.swipeToRefresh.isRefreshing = true
            is LoadingState.Success -> {
                binding.swipeToRefresh.isRefreshing = false
            }
        }
    }

    private fun listenToLocationResult() {
        setFragmentResultListener(Keys.LOCATION_INFO_CHANGED) { _, bundle ->
            bundle.getParcelable<LocationInfo>(Keys.LOCATION_INFO)?.let { locationInfo ->
                viewModel.onLocationSelected(locationInfo)
            }
        }
    }

    private fun navigateToSearchScreen() {
        findNavController().navigate(R.id.action_forecastFragment_to_locationPickerFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}