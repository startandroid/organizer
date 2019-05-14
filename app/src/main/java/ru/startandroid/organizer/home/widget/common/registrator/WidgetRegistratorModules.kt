package ru.startandroid.organizer.home.widget.common.registrator

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.startandroid.organizer.home.widget.widgets.*
import ru.startandroid.organizer.home.widget.common.WidgetEntityMapper
import ru.startandroid.organizer.home.widget.common.adapter.WidgetProvider
import ru.startandroid.organizer.home.widget.refresh.WidgetsRefresher

@Module
abstract class WidgetRegistratorModule {

    @Binds
    abstract fun provideToMapperRegistrator(widgetRegistrator: WidgetRegistratorImpl): WidgetEntityMapper.ToMapperRegistrator

    @Binds
    abstract fun provideToProviderRegistrator(widgetRegistrator: WidgetRegistratorImpl): WidgetProvider.ToProviderRegistrator

    @Binds
    abstract fun provideToRefresherRegistrator(widgetRegistrator: WidgetRegistratorImpl): WidgetsRefresher.ToRefresherRegistrator

}

@Module
class WidgetsModule() {

    @Provides
    fun provideWidgetRegistrator(
            testWidget1RegisterData: TestWidget1RegisterData,
            testWidget2RegisterData: TestWidget2RegisterData,
            weatherWidgetRegisterData: WeatherWidgetRegisterData
    ) = WidgetRegistratorImpl(
            listOf(
                    testWidget1RegisterData,
                    testWidget2RegisterData,
                    weatherWidgetRegisterData
            )
    )

}