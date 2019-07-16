package ru.startandroid.organizer.home.widget.di

import dagger.Module
import dagger.Provides
import ru.startandroid.widgets.testwidget1.TestWidget1WidgetMetadata
import ru.startandroid.widgets.testwidget2.TestWidget2WidgetMetadata
import ru.startandroid.widgets.weatherwidget.WeatherWidgetWidgetMetadata
import ru.startandroid.widgetsbase.registrator.WidgetMetadatRepositoryImpl

@Module
class WidgetsModule {

    @Provides
    fun provideWidgetsData(
            testWidget1RegisterData: ru.startandroid.widgets.testwidget1.TestWidget1WidgetMetadata,
            testWidget2RegisterData: ru.startandroid.widgets.testwidget2.TestWidget2WidgetMetadata,
            weatherWidgetRegisterData: ru.startandroid.widgets.weatherwidget.WeatherWidgetWidgetMetadata
    ): MutableSet<WidgetMetadatRepositoryImpl.WidgetMetadata> {
        return mutableSetOf(testWidget1RegisterData,
                testWidget2RegisterData,
                weatherWidgetRegisterData)
    }

}