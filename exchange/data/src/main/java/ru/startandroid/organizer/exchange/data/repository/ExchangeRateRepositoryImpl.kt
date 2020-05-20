package ru.startandroid.organizer.exchange.data.repository

import dagger.Binds
import ru.startandroid.organizer.exchange.data.network.ExchangeRateApi
import ru.startandroid.organizer.exchange.domain.repository.ExchangeRateRepository
import javax.inject.Inject

class ExchangeRateRepositoryImpl @Inject constructor(
        private val exchangeRateApi: ExchangeRateApi
): ExchangeRateRepository {

    override fun getExchangeRateSync(date: String, currencyFrom: String, currencyTo: String): String? {
        return exchangeRateApi.getRate(date, currencyFrom, currencyTo).execute().body()
    }

}