package ru.startandroid.organizer.home.widget.di

import dagger.Module
import dagger.Provides
import ru.startandroid.organizer.home.widget.TestWidget1WidgetMetadata
import ru.startandroid.organizer.home.widget.TestWidget2WidgetMetadata
import ru.startandroid.organizer.home.widget.WeatherWidgetWidgetMetadata
import ru.startandroid.widgets.registrator.WidgetMetadatRepositoryImpl

@Module
class WidgetsModule {

    @Provides
    fun provideWidgetsData(
            testWidget1RegisterData: TestWidget1WidgetMetadata,
            testWidget2RegisterData: TestWidget2WidgetMetadata,
            weatherWidgetRegisterData: WeatherWidgetWidgetMetadata
    ): MutableSet<WidgetMetadatRepositoryImpl.WidgetMetadata> {
        return mutableSetOf(testWidget1RegisterData,
                testWidget2RegisterData,
                weatherWidgetRegisterData)
    }

}