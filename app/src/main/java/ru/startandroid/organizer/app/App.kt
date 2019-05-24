package ru.startandroid.organizer.app

import android.app.Activity
import android.app.Application
import android.util.Log
import com.crashlytics.android.Crashlytics
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.fabric.sdk.android.Fabric
import ru.startandroid.organizer.app.di.AppModule
import ru.startandroid.organizer.app.di.ApplicationComponent
import ru.startandroid.organizer.app.di.DaggerApplicationComponent
import ru.startandroid.widgets.registrator.WidgetRegistratorData
import ru.startandroid.widgets.registrator.WidgetRegistratorImpl
import javax.inject.Inject


class App : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var widgetRegistratorData: WidgetRegistratorData
    @Inject
    lateinit var widgetData: MutableSet<WidgetRegistratorImpl.RegisterData>

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        initApplicationComponentAndInject()
        initFabric()
        initWidgets()
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
        Log.d("qweee", "App initWidgets, widgetRegistrator ${widgetRegistratorData}")
        widgetRegistratorData.putRegisterData(widgetData)
    }

}