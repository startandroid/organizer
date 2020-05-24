package ru.startandroid.widgets

import dagger.Module
import dagger.Provides
import ru.startandroid.organizer.exchange.presentation.di.ExchangeModule
import ru.startandroid.organizer.exchange.presentation.widget.ExchangeWidgetMetadataProvider
import ru.startandroid.widgets.testwidget1.TestWidget1MetadataProvider
import ru.startandroid.widgets.testwidget2.TestWidget2MetadataProvider
import ru.startandroid.widgets.weatherwidget.WeatherWidget1MetadataProvider
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadataProvider


// TODOL move it to app
// remove widgets module
// move all widgets to separate directories-modules
@Module(includes = [
    ExchangeModule::class
])
class WidgetsModule {

    @Provides
    fun provideWidgetsData(
            testWidget1MetadataProvider: TestWidget1MetadataProvider,
            testWidget2MetadataProvider: TestWidget2MetadataProvider,
            weatherWidget1MetadataProvider: WeatherWidget1MetadataProvider,
            exchangeWidgetMetadataProvider: ExchangeWidgetMetadataProvider
    ): MutableSet<WidgetMetadataProvider> {
        return mutableSetOf(
                exchangeWidgetMetadataProvider,
                testWidget1MetadataProvider,
                testWidget2MetadataProvider,
                weatherWidget1MetadataProvider

        )
    }

}