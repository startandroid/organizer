package ru.startandroid.organizer.exchange.presentation.di

import dagger.Module
import ru.startandroid.organizer.exchange.data.di.DataModule

@Module(includes = [DataModule::class])
class ExchangeModule {
}