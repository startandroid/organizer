package ru.startandroid.organizer.exchange.presentation.widget.content.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.startandroid.organizer.exchange.presentation.R
import ru.startandroid.organizer.exchange.presentation.widget.content.ExchangeRateWdgt
import javax.inject.Inject

class ExchangeRateListAdapter @Inject constructor(): ListAdapter<ExchangeRateWdgt, ExchangeRateListHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeRateListHolder {
        return ExchangeRateListHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.widget_exchange_rate_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ExchangeRateListHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffCallback = object: DiffUtil.ItemCallback<ExchangeRateWdgt>() {
            override fun areItemsTheSame(oldItem: ExchangeRateWdgt, newItem: ExchangeRateWdgt): Boolean {
                return oldItem.currencyFrom == newItem.currencyFrom && oldItem.currencyTo == newItem.currencyTo
            }

            override fun areContentsTheSame(oldItem: ExchangeRateWdgt, newItem: ExchangeRateWdgt): Boolean {
                return oldItem.rate == newItem.rate && oldItem.reverseRate == newItem.reverseRate
            }

        }

    }
}