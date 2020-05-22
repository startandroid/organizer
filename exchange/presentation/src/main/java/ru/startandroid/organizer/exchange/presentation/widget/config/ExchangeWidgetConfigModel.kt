package ru.startandroid.organizer.exchange.presentation.widget.config

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import ru.startandroid.device.SingleLiveEvent
import ru.startandroid.device.setIfNotExists
import ru.startandroid.device.updateValue
import ru.startandroid.widgetsbase.domain.model.WidgetConfig

class ExchangeWidgetConfigModel(
        private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val initData = savedStateHandle.get<ExchangeWidgetConfig>("config")!!
    val showReverse = savedStateHandle.getLiveData<Boolean>("showReverse")
    val rates = savedStateHandle.getLiveData<List<Pair<String, String>>>("rates")
    val showToast = SingleLiveEvent<String>()

    init {
        savedStateHandle.setIfNotExists("showReverse", initData.showReverse)
        savedStateHandle.setIfNotExists("rates", initData.rates)
    }

    fun addRate(fromCurrency: String, toCurrency: String) {
        if (fromCurrency == toCurrency) {
            showToast("The currencies are the same")
            return
        }

        rates.updateValue(emptyList()) { currentValue ->
            currentValue.toMutableList()
                    .apply {
                        add(fromCurrency to toCurrency)
                    }
        }
    }

    fun checkIfNewConfigIsValid(): Boolean {
        if (rates.value?.size != rates.value?.distinct()?.size) {
            showToast("There are duplicates")
            return false
        }
        return true
    }

    fun fillNewConfig(): WidgetConfig {
        return initData.copy(rates = rates.value ?: emptyList(), showReverse = showReverse.value
                ?: false)
    }

    fun deleteRateAtPosition(position: Int) {
        rates.updateValue(emptyList()) { currentValue ->
            currentValue.toMutableList()
                    .apply {
                        removeAt(position)
                    }
        }
    }

    private fun showToast(message: String) {
        showToast.value = message
    }

}