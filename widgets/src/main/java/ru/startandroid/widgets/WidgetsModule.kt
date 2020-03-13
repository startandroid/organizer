package ru.startandroid.widgets

import dagger.Module
import dagger.Provides
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadataProvider

@Module
class WidgetsModule {

    @Provides
    fun provideWidgetsData(
            testWidget1MetadataProvider: ru.startandroid.widgets.testwidget1.TestWidget1MetadataProvider,
            testWidget2MetadataProvider: ru.startandroid.widgets.testwidget2.TestWidget2MetadataProvider,
            weatherWidget1MetadataProvider: ru.startandroid.widgets.weatherwidget.WeatherWidget1MetadataProvider
    ): MutableSet<WidgetMetadataProvider> {
        return mutableSetOf(testWidget1MetadataProvider,
                testWidget2MetadataProvider,
                weatherWidget1MetadataProvider)
    }

}