package ru.startandroid.widgetsbase.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.startandroid.domain.ScopeApplication
import ru.startandroid.widgetsbase.db.WidgetDatabase
import ru.startandroid.widgetsbase.db.WidgetDbInitializer
import ru.startandroid.widgetsbase.metadata.*

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
    abstract fun provideWidgetRegistratorData(widgetMetadataRepository: WidgetMetadataRepository): WidgetRegistratorData


}

