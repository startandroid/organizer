package ru.startandroid.widgetsbase.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.startandroid.domain.ScopeApplication
import ru.startandroid.widgetsbase.data.db.WidgetDatabase
import ru.startandroid.widgetsbase.data.db.WidgetDbInitializer
import ru.startandroid.widgetsbase.data.db.mapper.WidgetConfigEntityMapper
import ru.startandroid.widgetsbase.data.db.mapper.WidgetDataEntityMapper
import ru.startandroid.widgetsbase.data.db.repository.WidgetConfigRepositoryImpl
import ru.startandroid.widgetsbase.data.db.repository.WidgetDataRepositoryImpl
import ru.startandroid.widgetsbase.data.metadata.*
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetDataRepository

@Module(includes = [WidgetMetadataProviderModule::class])
class WidgetsCommonModule {

    @ScopeApplication
    @Provides
    fun provideAppDatabase(context: Context, widgetDbInitializer: WidgetDbInitializer): WidgetDatabase {
        return widgetDbInitializer.createDatabase(context)
    }

    @Provides
    fun provideWidgetDataRepository(widgetDatabase: WidgetDatabase, widgetDataEntityMapper: WidgetDataEntityMapper): WidgetDataRepository
            = WidgetDataRepositoryImpl(widgetDatabase, widgetDataEntityMapper)

    @Provides
    fun provideWidgetConfigRepository(widgetDatabase: WidgetDatabase, widgetConfigEntityMapper: WidgetConfigEntityMapper): WidgetConfigRepository
            = WidgetConfigRepositoryImpl(widgetDatabase, widgetConfigEntityMapper)

}

@Module
abstract class WidgetMetadataProviderModule {

    @Binds
    abstract fun provideWidgetMetadataRepository(widgetMetadataRepository: WidgetMetadataRepositoryImpl): WidgetMetadataRepository

    @Binds
    abstract fun provideWidgetMappingMetadataRepository(widgetMetadataRepository: WidgetMetadataRepository): WidgetMappingMetadataRepository

    @Binds
    abstract fun provideWidgetContentMetadataRepository(widgetMetadataRepository: WidgetMetadataRepository): WidgetContentMetadataRepository

    @Binds
    abstract fun provideWidgetRefresherMetadataRepository(widgetMetadataRepository: WidgetMetadataRepository): WidgetDbDataHelperRepository

    @Binds
    abstract fun provideWidgetDbInitMetadataRepository(widgetMetadataRepository: WidgetMetadataRepository): WidgetDbInitMetadataRepository

    @Binds
    abstract fun provideWidgetConfigScreenMetadataRepository(widgetMetadataRepository: WidgetMetadataRepository): WidgetConfigScreenMetadataRepository

    @Binds
    abstract fun provideWidgetRegistratorData(widgetMetadataRepository: WidgetMetadataRepository): WidgetRegistratorMetadataRepository


}

