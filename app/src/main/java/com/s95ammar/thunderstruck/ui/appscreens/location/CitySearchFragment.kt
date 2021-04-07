package com.s95ammar.thunderstruck.ui.appscreens.location

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.s95ammar.thunderstruck.Keys
import com.s95ammar.thunderstruck.R
import com.s95ammar.thunderstruck.databinding.CitySearchFragmentBinding
import com.s95ammar.thunderstruck.ui.appscreens.location.adapter.SearchResultsAdapter
import com.s95ammar.thunderstruck.ui.appscreens.location.data.LocationInfo
import com.s95ammar.thunderstruck.ui.common.LoadingState
import com.s95ammar.thunderstruck.ui.common.ViewBindingUnavailableException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import com.s95ammar.thunderstruck.ui.appscreens.location.data.LocationSearchUiEvent as UiEvent

@AndroidEntryPoint
class CitySearchFragment : Fragment(R.layout.city_search_fragment) {

    private val viewModel: CitySearchViewModel by viewModels()

    private val adapter by lazy { SearchResultsAdapter(viewModel::onSearchResultClick) }

    private var _binding: CitySearchFragmentBinding? = null
    private val binding get() = _binding ?: throw ViewBindingUnavailableException()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = CitySearchFragmentBinding.bind(view)
        setUpViews()
        initObservers()
    }

    private fun setUpViews() {
        binding.locationSearchView.setOnQueryTextListener(getSearchViewListener())
        binding.searchRecyclerView.adapter = adapter
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
    }

    private fun getSearchViewListener(): SearchView.OnQueryTextListener {
        return object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getCitySearchResults(query?.trim().orEmpty())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    viewModel.getCitySearchResults(newText?.trim().orEmpty())
                    return true
                }
                return false
            }
        }
    }

    private fun initObservers() {
        viewModel.searchResults.observe(viewLifecycleOwner) { setSearchResults(it) }

        lifecycleScope.launchWhenStarted {
            viewModel.eventFlow.collect { handleUiEvent(it) }
        }
    }

    private fun handleUiEvent(uiEvent: UiEvent) {
        when (uiEvent) {
            is UiEvent.SetResult -> setResult(uiEvent.locationInfo)
            is UiEvent.Exit -> findNavController().navigateUp()
            is UiEvent.DisplayLoadingState -> displayLoadingState(uiEvent.loadingState)
        }
    }

    private fun displayLoadingState(loadingState: LoadingState) {
        when (loadingState) {
            is LoadingState.Error -> {
                val errorMessage = loadingState.throwable.localizedMessage ?: getString(R.string.error_occurred)
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                binding.progressBar.isVisible = false
            }
            is LoadingState.Loading -> binding.progressBar.isVisible = true
            is LoadingState.Success -> {
                binding.progressBar.isVisible = false
            }
        }
    }

    private fun setResult(locationInfo: LocationInfo) {
        setFragmentResult(Keys.LOCATION_INFO_CHANGED, bundleOf(Keys.LOCATION_INFO to locationInfo))
    }

    private fun setSearchResults(locationInfoList: List<LocationInfo>) {
        adapter.submitList(locationInfoList)
    }
}