package ru.startandroid.organizer.app

import android.app.Activity
import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.crashlytics.android.Crashlytics
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.fabric.sdk.android.Fabric
import ru.startandroid.organizer.app.di.AppModule
import ru.startandroid.organizer.app.di.ApplicationComponent
import ru.startandroid.organizer.app.di.DaggerApplicationComponent
import ru.startandroid.widgets.refresh.WidgetWorkerFactory
import ru.startandroid.widgets.registrator.WidgetMetadatRepositoryImpl
import ru.startandroid.widgets.registrator.WidgetRegistratorData
import javax.inject.Inject


class App : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var widgetRegistratorData: WidgetRegistratorData
    @Inject
    lateinit var widgetData: MutableSet<WidgetMetadatRepositoryImpl.WidgetMetadata>

    @Inject
    lateinit var workerFactory: WidgetWorkerFactory

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        initApplicationComponentAndInject()
        initFabric()
        initWidgets()
        initWorkManager()
    }

    private fun initApplicationComponentAndInject() {
        applicationComponent = DaggerApplicationComponent
                .builder()
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

    private fun initWidgets() {
        widgetRegistratorData.putRegisterData(widgetData)
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