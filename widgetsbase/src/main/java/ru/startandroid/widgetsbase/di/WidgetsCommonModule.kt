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
import ru.startandroid.widgetsbase.data.db.init.WidgetDbInitializer
import ru.startandroid.widgetsbase.data.db.mapper.*
import ru.startandroid.widgetsbase.data.db.repository.WidgetConfigRepositoryImpl
import ru.startandroid.widgetsbase.data.db.repository.WidgetDataRepositoryImpl
import ru.startandroid.widgetsbase.data.db.repository.WidgetRefreshStatusRepositoryImpl
import ru.startandroid.widgetsbase.data.db.workmanager.WidgetWorkManagerImpl
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadataRepository
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadataRepositoryImpl
import ru.startandroid.widgetsbase.data.metadata.WidgetRegistratorMetadataRepository
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
    fun provideWidgetDataRepository(
            widgetDatabase: WidgetDatabase,
            widgetDataEntityUiToDbMapper: WidgetDataEntityUiToDbMapper,
            widgetDataEntityDbToUiMapper: WidgetDataEntityDbToUiMapper,
            widgetDataExtendedEntityDbToUiMapper: WidgetDataExtendedEntityDbToUiMapper,
            dbScheduler: Scheduler
    ): WidgetDataRepository =
            WidgetDataRepositoryImpl(widgetDatabase, widgetDataEntityUiToDbMapper, widgetDataEntityDbToUiMapper, widgetDataExtendedEntityDbToUiMapper, dbScheduler)

    @ScopeApplication
    @Provides
    fun provideWidgetConfigRepository(
            widgetDatabase: WidgetDatabase,
            widgetConfigEntityUiToDbMapper: WidgetConfigEntityUiToDbMapper,
            widgetConfigEntityDbToUiMapper: WidgetConfigEntityDbToUiMapper,
            dbScheduler: Scheduler,
            analytics: Analytics
    ): WidgetConfigRepository =
            WidgetConfigRepositoryImpl(widgetDatabase, widgetConfigEntityUiToDbMapper, widgetConfigEntityDbToUiMapper, dbScheduler, analytics)

    @ScopeApplication
    @Provides
    fun provideWidgetRefreshStatusRepository(widgetDatabase: WidgetDatabase): WidgetRefreshStatusRepository =
            WidgetRefreshStatusRepositoryImpl(widgetDatabase)

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
    fun provideWidgetWorkManager(workManagerProvider: dagger.Lazy<WorkManager>, widgetMetadataRepository: WidgetMetadataRepository): WidgetWorkManager = WidgetWorkManagerImpl(workManagerProvider, widgetMetadataRepository)

}

@Module
abstract class WidgetMetadataProviderModule {

    @Binds
    abstract fun provideWidgetMetadataRepository(widgetMetadataRepository: WidgetMetadataRepositoryImpl): WidgetMetadataRepository

    @Binds
    abstract fun provideWidgetRegistratorMetadataRepository(widgetMetadataRepository: WidgetMetadataRepositoryImpl): WidgetRegistratorMetadataRepository

}

