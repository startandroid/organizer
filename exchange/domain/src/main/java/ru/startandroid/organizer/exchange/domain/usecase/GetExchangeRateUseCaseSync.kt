package ru.startandroid.organizer.exchange.domain.usecase

import ru.startandroid.organizer.exchange.domain.repository.ExchangeRateRepository
import javax.inject.Inject

class GetExchangeRateUseCaseSync @Inject constructor(
        private val exchangeRateRepository: ExchangeRateRepository
) {

    fun invoke(date: String, currencyFrom: String, currencyTo: String): String? {
        val rate: String? = exchangeRateRepository.getExchangeRateSync(date, currencyFrom, currencyTo)
        return rate?.let { "%.2f".format(it.toFloat()) }
    }
}