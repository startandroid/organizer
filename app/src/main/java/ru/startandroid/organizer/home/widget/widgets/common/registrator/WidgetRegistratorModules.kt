package ru.startandroid.organizer.home.widget.widgets.common.registrator

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.startandroid.organizer.home.widget.widgets.*
import ru.startandroid.organizer.home.widget.widgets.common.WidgetEntityMapper
import ru.startandroid.organizer.home.widget.widgets.common.adapter.WidgetProvider

@Module
abstract class WidgetRegistratorModule {

    @Binds
    abstract fun provideToMapperRegistrator(widgetRegistrator: WidgetRegistratorImpl): WidgetEntityMapper.ToMapperRegistrator

    @Binds
    abstract fun provideToMProviderRegistrator(widgetRegistrator: WidgetRegistratorImpl): WidgetProvider.ToProviderRegistrator

}

@Module
class WidgetsModule() {

    @Provides
    fun provideWidgetRegistrator(
            testWidget1RegisterData: TestWidget1RegisterData,
            testWidget2RegisterData: TestWidget2RegisterData
    ) = WidgetRegistratorImpl(
            listOf(
                    testWidget1RegisterData,
                    testWidget2RegisterData
            )
    )

}