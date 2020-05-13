package ru.startandroid.organizer.app

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.crashlytics.android.Crashlytics
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import io.fabric.sdk.android.Fabric
import ru.startandroid.organizer.app.di.AppModule
import ru.startandroid.organizer.app.di.ApplicationComponent
import ru.startandroid.organizer.app.di.DaggerApplicationComponent
import ru.startandroid.organizer.exchange.presentation.di.DaggerWidgetComponent
import ru.startandroid.organizer.exchange.presentation.di.WidgetComponent
import ru.startandroid.widgetsbase.data.db.workmanager.WidgetWorkerFactory
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadataProvider
import ru.startandroid.widgetsbase.data.metadata.WidgetRegistratorMetadataRepository
import javax.inject.Inject


class App : Application(), HasAndroidInjector {

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var widgetRegistratorMetadataRepository: WidgetRegistratorMetadataRepository
    @Inject
    lateinit var widgetMetadataProviders: MutableSet<WidgetMetadataProvider>

    @Inject
    lateinit var workerFactory: WidgetWorkerFactory

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        initApplicationComponentAndInject()
        initFabric()
        initWidgetsMetadataFactories()
        initWorkManager()
    }

    private fun initApplicationComponentAndInject() {
        applicationComponent = DaggerApplicationComponent
                .builder()
                .widgetComponent(DaggerWidgetComponent.create())
                .appModule(AppModule(this))
                .build()
        applicationComponent.injectApp(this)
    }

    private fun initFabric() {
        val fabric = Fabric.Builder(this)
                .kits(Crashlytics())
                .debuggable(true)  // Enables Crashlytics debugger
                .build()
        Fabric.with(fabric)
    }

    private fun initWidgetsMetadataFactories() {
        widgetRegistratorMetadataRepository.registerMetadata(widgetMetadataProviders)
    }

    private fun initWorkManager() {
        // provide custom configuration
        val config = Configuration.Builder()
                //.setMinimumLoggingLevel(android.util.Log.INFO)
                .setWorkerFactory(workerFactory)
                .build()

        // initialize WorkManager
        WorkManager.initialize(this, config)
    }

}