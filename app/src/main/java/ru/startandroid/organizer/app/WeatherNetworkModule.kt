package ru.startandroid.organizer.app


import dagger.Module
import dagger.Provides
import ru.startandroid.data.WeatherAPI
import javax.inject.Named


@Module
class WeatherNetworkModule {

    @Provides
    @Named("weather")
    internal fun provideNetwork(): WeatherAPI {

        return WeatherAPI.create()
    }

}
