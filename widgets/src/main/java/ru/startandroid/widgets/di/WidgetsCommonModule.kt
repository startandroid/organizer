package ru.startandroid.widgets.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.startandroid.domain.ScopeApplication
import ru.startandroid.widgets.WidgetEntityMapper
import ru.startandroid.widgets.adapter.WidgetProvider
import ru.startandroid.widgets.db.WidgetDatabase
import ru.startandroid.widgets.db.WidgetDbInitalizer
import ru.startandroid.widgets.refresh.WidgetsRefresher
import ru.startandroid.widgets.registrator.WidgetRegistrator
import ru.startandroid.widgets.registrator.WidgetRegistratorData
import ru.startandroid.widgets.registrator.WidgetRegistratorImpl

@Module(includes = [WidgetRegistratorModule::class])
class WidgetsCommonModule {

    @ScopeApplication
    @Provides
    fun provideAppDatabase(context: Context, widgetDbInitalizer: WidgetDbInitalizer): WidgetDatabase {
        return widgetDbInitalizer.createDatabase(context)
    }

}

@Module
abstract class WidgetRegistratorModule {

    @Binds
    abstract fun provide(widgetRegistrator: WidgetRegistratorImpl): WidgetRegistrator

    @Binds
    abstract fun provideToMapperRegistrator(widgetRegistrator: WidgetRegistrator): WidgetEntityMapper.ToMapperRegistrator

    @Binds
    abstract fun provideToProviderRegistrator(widgetRegistrator: WidgetRegistrator): WidgetProvider.ToProviderRegistrator

    @Binds
    abstract fun provideToRefresherRegistrator(widgetRegistrator: WidgetRegistrator): WidgetsRefresher.ToRefresherRegistrator

    @Binds
    abstract fun provideToDbInitializer(widgetRegistrator: WidgetRegistrator): WidgetDbInitalizer.ToDbInitializerRegistrator

    @Binds
    abstract fun provideWidgetRegistratorData(widgetRegistrator: WidgetRegistrator): WidgetRegistratorData


}

