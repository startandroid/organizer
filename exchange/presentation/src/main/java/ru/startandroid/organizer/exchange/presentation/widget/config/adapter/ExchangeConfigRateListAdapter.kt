package ru.startandroid.organizer.exchange.presentation.widget.config.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.startandroid.organizer.exchange.presentation.R
import javax.inject.Inject

class ExchangeConfigRateListAdapter @Inject constructor(
        private val onDelete: (Int) -> Unit
): ListAdapter<Pair<String, String>, ExchangeConfigRateListHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeConfigRateListHolder {
        return ExchangeConfigRateListHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.widget_exchange_config_rates_item, parent, false),
                onDelete
        )
    }

    override fun onBindViewHolder(holder: ExchangeConfigRateListHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffCallback = object: DiffUtil.ItemCallback<Pair<String, String>>() {
            override fun areItemsTheSame(oldItem: Pair<String, String>, newItem: Pair<String, String>): Boolean {
                return oldItem.first == newItem.first && oldItem.second == newItem.second
            }

            override fun areContentsTheSame(oldItem: Pair<String, String>, newItem: Pair<String, String>): Boolean {
                return true
            }

        }

    }
}