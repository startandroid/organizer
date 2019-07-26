package ru.startandroid.widgets

import dagger.Module
import dagger.Provides
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadata

@Module
class WidgetsModule {

    @Provides
    fun provideWidgetsData(
            testWidget1RegisterData: ru.startandroid.widgets.testwidget1.TestWidget1WidgetMetadata,
            testWidget2RegisterData: ru.startandroid.widgets.testwidget2.TestWidget2WidgetMetadata,
            weatherWidgetRegisterData: ru.startandroid.widgets.weatherwidget.WeatherWidgetWidgetMetadata
    ): MutableSet<WidgetMetadata> {
        return mutableSetOf(testWidget1RegisterData,
                testWidget2RegisterData,
                weatherWidgetRegisterData)
    }

}