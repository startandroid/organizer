package ru.startandroid.organizer.exchange.presentation.widget.content.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.widget_exchange_rate_item.*
import ru.startandroid.organizer.exchange.presentation.widget.content.ExchangeRateWdgt

class ExchangeRateListHolder(
        override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(item: ExchangeRateWdgt) {
        textCurrencies.text = "${item.currencyFrom}/${item.currencyTo}"
        textRate.text = item.rate
        textReverseRate.text = item.reverseRate
    }
}