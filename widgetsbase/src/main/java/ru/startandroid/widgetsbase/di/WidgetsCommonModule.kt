package ru.startandroid.widgetsbase.di

import android.content.Context
import androidx.work.WorkManager
import com.startandroid.dialoghelper.DialogHelper
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import ru.startandroid.device.analytics.Analytics
import ru.startandroid.domain.ScopeApplication
import ru.startandroid.widgetsbase.data.db.WidgetDatabase
import ru.startandroid.widgetsbase.data.db.WidgetDbInitializer
import ru.startandroid.widgetsbase.data.db.mapper.WidgetConfigEntityMapper
import ru.startandroid.widgetsbase.data.db.mapper.WidgetDataEntityMapper
import ru.startandroid.widgetsbase.data.db.refresh.WidgetWorkManagerImpl
import ru.startandroid.widgetsbase.data.db.repository.WidgetConfigRepositoryImpl
import ru.startandroid.widgetsbase.data.db.repository.WidgetDataRepositoryImpl
import ru.startandroid.widgetsbase.data.db.repository.WidgetRefreshStatusRepositoryImpl
import ru.startandroid.widgetsbase.data.metadata.*
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetDataRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetRefreshStatusRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetWorkManager
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@Module(includes = [WidgetMetadataProviderModule::class])
class WidgetsCommonModule {

    @ScopeApplication
    @Provides
    fun provideAppDatabase(context: Context, widgetDbInitializer: WidgetDbInitializer): WidgetDatabase {
        return widgetDbInitializer.createDatabase(context)
    }

    @ScopeApplication
    @Provides
    fun provideWidgetDataRepository(widgetDatabase: WidgetDatabase, widgetDataEntityMapper: WidgetDataEntityMapper): WidgetDataRepository = WidgetDataRepositoryImpl(widgetDatabase, widgetDataEntityMapper)

    @ScopeApplication
    @Provides
    fun provideWidgetConfigRepository(widgetDatabase: WidgetDatabase, widgetConfigEntityMapper: WidgetConfigEntityMapper, dbScheduler: Scheduler, analytics: Analytics): WidgetConfigRepository = WidgetConfigRepositoryImpl(widgetDatabase, widgetConfigEntityMapper, dbScheduler, analytics)

    @ScopeApplication
    @Provides
    fun provideWidgetRefreshStatusRepository(widgetDatabase: WidgetDatabase): WidgetRefreshStatusRepository = WidgetRefreshStatusRepositoryImpl(widgetDatabase)

    @ScopeApplication
    @Provides
    fun provideDbScheduler(dbExecutor: Executor) = Schedulers.from(dbExecutor)

    @Provides
    fun provideDialogHelper() = DialogHelper()

    @ScopeApplication
    @Provides
    fun provideDbExecutor(): Executor = Executors.newSingleThreadExecutor()

    @ScopeApplication
    @Provides
    fun provideWidgetWorkManager(workManagerProvider: dagger.Lazy<WorkManager>, widgetRefreshParametersMetadataRepository: WidgetRefreshParametersMetadataRepository): WidgetWorkManager
            = WidgetWorkManagerImpl(workManagerProvider, widgetRefreshParametersMetadataRepository)

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

    @Binds
    abstract fun provideWidgetRefreshParametersMetadataRepository(widgetMetadataRepository: WidgetMetadataRepository): WidgetRefreshParametersMetadataRepository

}

