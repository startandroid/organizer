package ru.startandroid.widgetsbase.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.startandroid.domain.ScopeApplication
import ru.startandroid.widgetsbase.adapter.content.WidgetProvider
import ru.startandroid.widgetsbase.db.WidgetDatabase
import ru.startandroid.widgetsbase.db.WidgetDbInitializer
import ru.startandroid.widgetsbase.mapper.WidgetEntityMapper
import ru.startandroid.widgetsbase.refresh.WidgetWorkerFactory
import ru.startandroid.widgetsbase.registrator.WidgetMetadatRepositoryImpl
import ru.startandroid.widgetsbase.registrator.WidgetMetadataRepository
import ru.startandroid.widgetsbase.registrator.WidgetRegistratorData

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

