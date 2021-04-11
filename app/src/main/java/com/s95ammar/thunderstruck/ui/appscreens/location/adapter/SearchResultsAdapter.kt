package com.s95ammar.thunderstruck.ui.appscreens.location.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.s95ammar.thunderstruck.R
import com.s95ammar.thunderstruck.databinding.ItemLocationSearchBinding
import com.s95ammar.thunderstruck.ui.appscreens.location.data.LocationInfo

class SearchResultsAdapter(
    private val onLocationClick: (position: Int) -> Unit
) : ListAdapter<LocationInfo, SearchResultsAdapter.SearchResultsViewHolder>(SearchResultsDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_location_search, parent, false)
        return SearchResultsViewHolder(onLocationClick, ItemLocationSearchBinding.bind(view))
    }

    override fun onBindViewHolder(holder: SearchResultsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class SearchResultsViewHolder(
        private val onLocationClick: (position: Int) -> Unit,
        private val binding: ItemLocationSearchBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.locationSearchResultTextView.setOnClickListener { onLocationClick(adapterPosition) }
        }

        fun bind(item: LocationInfo) {
            binding.locationSearchResultTextView.text = itemView.resources.getString(
                R.string.format_location,
                item.city,
                item.administrativeArea,
                item.country
            )
        }
    }

    class SearchResultsDiffUtil : DiffUtil.ItemCallback<LocationInfo>() {

        override fun areItemsTheSame(oldItem: LocationInfo, newItem: LocationInfo): Boolean {
            return oldItem.city == newItem.city
        }

        override fun areContentsTheSame(oldItem: LocationInfo, newItem: LocationInfo): Boolean {
            return oldItem == newItem
        }
    }
}
