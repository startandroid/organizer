package ru.startandroid.data.network.di


import dagger.Module
import dagger.Provides
import ru.startandroid.data.network.PlacesAPI
import ru.startandroid.data.network.WeatherAPI


@Module
class NetworkModule {

    @Provides
    internal fun provideNetwork(): WeatherAPI {
        return WeatherAPI.create()
    }

    @Provides
    internal fun providePlaces(): PlacesAPI {
        return PlacesAPI.create()
    }

}
