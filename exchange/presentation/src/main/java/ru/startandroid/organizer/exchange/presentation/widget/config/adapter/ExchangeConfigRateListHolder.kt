package ru.startandroid.organizer.exchange.presentation.widget.config.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.widget_exchange_config_rates_item.*

class ExchangeConfigRateListHolder(
        override val containerView: View,
        private val onDelete: (Int) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    init {
        btnDelete.setOnClickListener {
            onDelete.invoke(adapterPosition)
        }
    }

    fun bind(item: Pair<String, String>) {
        textRate.text = "${item.first} / ${item.second}"
    }
}