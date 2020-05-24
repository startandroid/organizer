package ru.startandroid.organizer.exchange.presentation.widget.update

import ru.startandroid.organizer.exchange.presentation.widget.config.ExchangeWidgetConfig
import ru.startandroid.organizer.exchange.presentation.widget.content.ExchangeRateWdgt
import ru.startandroid.organizer.exchange.presentation.widget.content.ExchangeWidgetData
import ru.startandroid.widgetsbase.data.db.correct.WidgetCorrect
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.domain.model.WidgetData
import javax.inject.Inject

class ExchangeWidgetCorrect @Inject constructor() : WidgetCorrect {

    override fun correctDataAccordingToConfig(currentWidgetData: WidgetData, widgetConfig: WidgetConfig): WidgetData {
        val config = (widgetConfig as ExchangeWidgetConfig)
        val currentData = (currentWidgetData as ExchangeWidgetData)
        val newRates = config.rates.mapNotNull { configCurrency ->
            var rate = currentData.rates.find {
                it.currencyFrom == configCurrency.first && it.currencyTo == configCurrency.second
            }
            if (rate == null) {
                rate = ExchangeRateWdgt(configCurrency.first, configCurrency.second, "", "")
            }
            rate
        }
        return currentData.copy(rates = newRates)
    }

}