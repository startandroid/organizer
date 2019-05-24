package ru.startandroid.data.network.di


import dagger.Module
import dagger.Provides
import ru.startandroid.data.network.WeatherAPI


@Module
class NetworkModule {

    @Provides
    internal fun provideNetwork(): WeatherAPI {

        return WeatherAPI.create()
    }

}
