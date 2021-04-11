package com.s95ammar.thunderstruck.ui.appscreens.forcast.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.s95ammar.thunderstruck.R
import com.s95ammar.thunderstruck.databinding.ItemDailyForecastBinding
import com.s95ammar.thunderstruck.model.common.IntIconType
import com.s95ammar.thunderstruck.ui.appscreens.forcast.data.DailyForecast
import com.s95ammar.thunderstruck.util.DateUtil
import java.util.Date

class DailyForecastAdapter : ListAdapter<DailyForecast, DailyForecastAdapter.DailyForecastViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_daily_forecast, parent, false)
        return DailyForecastViewHolder(ItemDailyForecastBinding.bind(view))
    }

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class DailyForecastViewHolder(private val binding: ItemDailyForecastBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DailyForecast) {
            setDayAndDate(item)
            setDayWeather(item)
            setNightWeather(item)
        }

        private fun setDayAndDate(item: DailyForecast) {
            val date = Date(item.timestampUnixMs)

            binding.dayTextView.text = DateUtil.getDayOfWeekAbbr(date)
            binding.dateTextView.text = DateUtil.getDateShortFormat(date)
        }

        private fun setDayWeather(item: DailyForecast) {
            binding.temperatureDayTextView.text = itemView.resources.getString(
                R.string.format_temperature_with_unit,
                item.maxTemperature,
                item.temperatureUnit
            )
            binding.weatherDayImageView.setImageResource(getImageResIdByIconType(item.dayIconType))
        }

        private fun setNightWeather(item: DailyForecast) {
            binding.temperatureNightTextView.text = itemView.resources.getString(
                R.string.format_temperature_with_unit,
                item.minTemperature,
                item.temperatureUnit
            )
            binding.weatherNightImageView.setImageResource(getImageResIdByIconType(item.nightIconType))
        }

        @DrawableRes
        private fun getImageResIdByIconType(@IntIconType iconType: Int): Int {
            return when (iconType) {
                IntIconType.SUNNY -> R.drawable.sunny
                IntIconType.MOSTLY_SUNNY -> R.drawable.mostly_sunny
                IntIconType.PARTLY_SUNNY -> R.drawable.partly_sunny
                IntIconType.INTERMITTENT_CLOUDS_DAY -> R.drawable.intermittent_clouds_day
                IntIconType.HAZY_SUNSHINE -> R.drawable.hazy_sunshine
                IntIconType.MOSTLY_CLOUDY_DAY -> R.drawable.mostly_cloudy_day
                IntIconType.CLOUDY -> R.drawable.cloudy
                IntIconType.DREARY -> R.drawable.dreary
                IntIconType.FOG -> R.drawable.fog
                IntIconType.SHOWERS -> R.drawable.showers
                IntIconType.MOSTLY_CLOUDY_W_SHOWERS_DAY -> R.drawable.mostly_cloudy_w_showers_day
                IntIconType.PARTLY_SUNNY_W_SHOWERS -> R.drawable.partly_sunny_w_showers
                IntIconType.T_STORMS -> R.drawable.t_storms
                IntIconType.MOSTLY_CLOUDY_W_T_STORMS_DAY -> R.drawable.mostly_cloudy_w_t_storms_day
                IntIconType.PARTLY_SUNNY_W_T_STORMS -> R.drawable.partly_sunny_w_t_storms
                IntIconType.RAIN -> R.drawable.rain
                IntIconType.FLURRIES -> R.drawable.flurries
                IntIconType.MOSTLY_CLOUDY_W_FLURRIES_DAY -> R.drawable.mostly_cloudy_w_flurries_day
                IntIconType.PARTLY_SUNNY_W_FLURRIES -> R.drawable.partly_sunny_w_flurries
                IntIconType.SNOW -> R.drawable.snow
                IntIconType.MOSTLY_CLOUDY_W_SNOW_DAY -> R.drawable.mostly_cloudy_w_snow_day
                IntIconType.ICE -> R.drawable.ice
                IntIconType.SLEET -> R.drawable.sleet
                IntIconType.FREEZING_RAIN -> R.drawable.freezing_rain
                IntIconType.RAIN_AND_SNOW -> R.drawable.rain_and_snow
                IntIconType.HOT -> R.drawable.hot
                IntIconType.COLD -> R.drawable.cold
                IntIconType.WINDY -> R.drawable.windy
                IntIconType.CLEAR -> R.drawable.clear
                IntIconType.MOSTLY_CLEAR -> R.drawable.mostly_clear
                IntIconType.PARTLY_CLOUDY -> R.drawable.partly_cloudy
                IntIconType.INTERMITTENT_CLOUDS_NIGHT -> R.drawable.intermittent_clouds_night
                IntIconType.HAZY_MOONLIGHT -> R.drawable.hazy_moonlight
                IntIconType.MOSTLY_CLOUDY_NIGHT -> R.drawable.mostly_cloudy_night
                IntIconType.PARTLY_CLOUDY_W_SHOWERS -> R.drawable.partly_cloudy_w_showers
                IntIconType.MOSTLY_CLOUDY_W_SHOWERS_NIGHT -> R.drawable.mostly_cloudy_w_showers_night
                IntIconType.PARTLY_CLOUDY_W_T_STORMS -> R.drawable.partly_cloudy_w_t_storms
                IntIconType.MOSTLY_CLOUDY_W_T_STORMS_NIGHT -> R.drawable.mostly_cloudy_w_t_storms_night
                IntIconType.MOSTLY_CLOUDY_W_FLURRIES_NIGHT -> R.drawable.mostly_cloudy_w_flurries_night
                IntIconType.MOSTLY_CLOUDY_W_SNOW_NIGHT -> R.drawable.mostly_cloudy_w_snow_night
                else -> 0
            }
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<DailyForecast>() {
        override fun areItemsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {
            return oldItem.timestampUnixMs == newItem.timestampUnixMs
        }

        override fun areContentsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {
            return oldItem == newItem
        }
    }
}
