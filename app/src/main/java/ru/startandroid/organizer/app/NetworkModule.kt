package ru.startandroid.organizer.app


import dagger.Module
import dagger.Provides
import ru.startandroid.data.WeatherAPI


@Module
class NetworkModule {

    @Provides
    internal fun provideNetwork(): WeatherAPI {

        return WeatherAPI.create()
    }

}
