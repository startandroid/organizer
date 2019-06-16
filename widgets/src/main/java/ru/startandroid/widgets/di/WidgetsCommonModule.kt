package ru.startandroid.widgets.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.startandroid.domain.ScopeApplication
import ru.startandroid.widgets.adapter.content.WidgetProvider
import ru.startandroid.widgets.db.WidgetDatabase
import ru.startandroid.widgets.db.WidgetDbInitializer
import ru.startandroid.widgets.mapper.WidgetEntityMapper
import ru.startandroid.widgets.refresh.WidgetWorkerFactory
import ru.startandroid.widgets.registrator.WidgetMetadatRepositoryImpl
import ru.startandroid.widgets.registrator.WidgetMetadataRepository
import ru.startandroid.widgets.registrator.WidgetRegistratorData

@Module(includes = [WidgetRegistratorModule::class])
class WidgetsCommonModule {

    @ScopeApplication
    @Provides
    fun provideAppDatabase(context: Context, widgetDbInitializer: WidgetDbInitializer): WidgetDatabase {
        return widgetDbInitializer.createDatabase(context)
    }

}

@Module
abstract class WidgetRegistratorModule {

    @Binds
    abstract fun provide(widgetMetadatRepository: WidgetMetadatRepositoryImpl): WidgetMetadataRepository

    @Binds
    abstract fun provideWidgetMappingMetadataRepository(widgetMetadataRepository: WidgetMetadataRepository): WidgetEntityMapper.WidgetMappingMetadataRepository

    @Binds
    abstract fun provideWidgetContentMetadataRepository(widgetMetadataRepository: WidgetMetadataRepository): WidgetProvider.WidgetContentMetadataRepository

    @Binds
    abstract fun provideWidgetRefresherMetadataRepository(widgetMetadataRepository: WidgetMetadataRepository): WidgetWorkerFactory.WidgetDbDataHelperRepository

    @Binds
    abstract fun provideWidgetDbInitMetadataRepository(widgetMetadataRepository: WidgetMetadataRepository): WidgetDbInitializer.WidgetDbInitMetadataRepository

    @Binds
    abstract fun provideWidgetRegistratorData(widgetMetadataRepository: WidgetMetadataRepository): WidgetRegistratorData


}

