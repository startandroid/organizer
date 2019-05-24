package ru.startandroid.organizer.home.widget.di

import dagger.Module
import dagger.Provides
import ru.startandroid.organizer.home.widget.TestWidget1RegisterData
import ru.startandroid.organizer.home.widget.TestWidget2RegisterData
import ru.startandroid.organizer.home.widget.widgets.WeatherWidgetRegisterData
import ru.startandroid.widgets.registrator.WidgetRegistratorImpl

@Module
class WidgetsModule {

    @Provides
    fun provideWidgetsData(
            testWidget1RegisterData: TestWidget1RegisterData,
            testWidget2RegisterData: TestWidget2RegisterData,
            weatherWidgetRegisterData: WeatherWidgetRegisterData
    ): MutableSet<WidgetRegistratorImpl.RegisterData> {
        return mutableSetOf(testWidget1RegisterData,
                testWidget2RegisterData,
                weatherWidgetRegisterData)
    }

}