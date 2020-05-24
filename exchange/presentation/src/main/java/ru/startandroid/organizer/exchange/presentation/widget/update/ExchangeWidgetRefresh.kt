package ru.startandroid.organizer.exchange.presentation.widget.update

import ru.startandroid.organizer.exchange.domain.usecase.GetExchangeRateUseCaseSync
import ru.startandroid.organizer.exchange.presentation.widget.config.ExchangeWidgetConfig
import ru.startandroid.organizer.exchange.presentation.widget.content.ExchangeRateWdgt
import ru.startandroid.organizer.exchange.presentation.widget.content.ExchangeWidgetData
import ru.startandroid.widgetsbase.data.db.refresh.WidgetRefresh
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.domain.model.WidgetData
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ExchangeWidgetRefresh @Inject constructor(
        private val getExchangeRateUseCaseSync: GetExchangeRateUseCaseSync
) : WidgetRefresh {

    val sdf = SimpleDateFormat("yyyy-MM-dd")

    override fun refreshData(currentWidgetData: WidgetData, widgetConfig: WidgetConfig): WidgetData? {
        val config = (widgetConfig as ExchangeWidgetConfig)
        val currentData = (currentWidgetData as ExchangeWidgetData)

        val date = sdf.format(Date())
        val rates = mutableListOf<ExchangeRateWdgt>()

        var exception: Throwable? = null
        for (currenciesPair in config.rates) {
            val result = getRate(date, currenciesPair.first, currenciesPair.second, config.showReverse)

            if (result.isSuccess) {
                result.getOrNull()?.let { rates.add(it) }
            } else {
                exception = result.exceptionOrNull() ?: Exception("Unknown error")
                break
            }
        }

        if (exception != null) {
            return currentData.copy(errorMessage = "Last update failed. ${exception.localizedMessage ?: ""}")
        } else {
            return ExchangeWidgetData(date, rates, "")
        }


    }

    private fun getRate(date: String, currencyFrom: String, currencyTo: String, showReverse: Boolean): Result<ExchangeRateWdgt> {
        var rate = ""
        var reverseRate = ""
        var exception: Exception? = null
        try {
            rate = getExchangeRateUseCaseSync.invoke(date, currencyFrom, currencyTo) ?: ""
            if (showReverse) {
                reverseRate = getExchangeRateUseCaseSync.invoke(date, currencyTo, currencyFrom)
                        ?: ""
            }
        } catch (e: Exception) {
            exception = e
        }
        if (rate.isEmpty() || (reverseRate.isEmpty() && showReverse)) {
            return Result.failure(exception ?: Exception("Unknown error"))
        } else {
            return Result.success(ExchangeRateWdgt(currencyFrom, currencyTo, rate, reverseRate))
        }

    }


}