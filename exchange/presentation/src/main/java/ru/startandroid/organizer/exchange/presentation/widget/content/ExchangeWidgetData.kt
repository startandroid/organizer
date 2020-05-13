package ru.startandroid.organizer.exchange.presentation.widget.content

import ru.startandroid.widgetsbase.domain.model.WidgetData

data class ExchangeWidgetData(
    val updateDate: String,
    val rates: List<ExchangeRateWdgt>,
    val errorMessage: String
) : WidgetData()

data class ExchangeRateWdgt(
        val currencyFrom: String,
        val currencyTo: String,
        val rate: String,
        val reverseRate: String
)