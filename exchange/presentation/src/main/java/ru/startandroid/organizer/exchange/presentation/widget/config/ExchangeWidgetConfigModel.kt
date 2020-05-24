package ru.startandroid.organizer.exchange.presentation.widget.config

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.startandroid.device.SingleLiveEvent
import ru.startandroid.widgetsbase.domain.model.WidgetConfig

class ExchangeWidgetConfigModel : ViewModel() {

    private lateinit var initData: ExchangeWidgetConfig
    var showReverse = false
    private val ratesLiveData = MutableLiveData<List<Pair<String, String>>>()
    val showToast = SingleLiveEvent<String>()

    fun init(initialData: ExchangeWidgetConfig) {
        if (!::initData.isInitialized) {
            initData = initialData
            ratesLiveData.value = initData.rates
            showReverse = initData.showReverse
        }
    }

    fun addRate(fromCurrency: String, toCurrency: String) {
        //showReversed.set(!showReversed.get())
        if (fromCurrency == toCurrency) {
            showToast("The currencies are the same")
            return
        }
        ratesLiveData.value = ratesLiveData.value
                ?.toMutableList()
                ?.apply {
                    add(fromCurrency to toCurrency)
                }
    }

    fun getRates() = ratesLiveData as LiveData<List<Pair<String, String>>>

    fun checkIfNewConfigIsValid(): Boolean {
        if (ratesLiveData.value?.size != ratesLiveData.value?.distinct()?.size) {
            showToast("There are duplicates")
            return false
        }
        return true
    }

    fun fillNewConfig(): WidgetConfig {
        return initData.copy(rates = ratesLiveData.value ?: emptyList(), showReverse = showReverse)
    }

    fun deleteRateAtPosition(position: Int) {
        ratesLiveData.value = ratesLiveData.value
                ?.toMutableList()
                ?.apply {
                    removeAt(position)
                }
    }

    private fun showToast(message: String) {
        showToast.value = message
    }

}