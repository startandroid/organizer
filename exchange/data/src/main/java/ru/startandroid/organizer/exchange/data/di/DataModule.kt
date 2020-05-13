package ru.startandroid.organizer.exchange.data.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import ru.startandroid.organizer.exchange.data.network.ExchangeRateApi
import ru.startandroid.organizer.exchange.data.repository.ExchangeRateRepositoryImpl
import ru.startandroid.organizer.exchange.domain.repository.ExchangeRateRepository
import javax.inject.Named

@Module
class DataModule {

    @Provides
    fun provideExchangeRateRepository(exchangeRateRepositoryImpl: ExchangeRateRepositoryImpl): ExchangeRateRepository {
        return exchangeRateRepositoryImpl
    }

     @Provides
    fun provideExchangeRateApi(): ExchangeRateApi {
        return Retrofit.Builder()
                .baseUrl("https://currencies.apps.grandtrunk.net")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build().create(ExchangeRateApi::class.java)
    }

}