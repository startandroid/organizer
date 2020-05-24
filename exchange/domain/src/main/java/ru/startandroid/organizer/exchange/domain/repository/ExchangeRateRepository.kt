package ru.startandroid.organizer.exchange.domain.repository

interface ExchangeRateRepository {
    fun getExchangeRateSync(date: String, currencyFrom: String, currencyTo: String): String?
}