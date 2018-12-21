package ru.startandroid.organizer.app

import android.app.Activity
import android.app.Application
import android.content.BroadcastReceiver
import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject
import io.fabric.sdk.android.Fabric
import com.crashlytics.android.Crashlytics
import dagger.android.HasBroadcastReceiverInjector


class App: Application(), HasActivityInjector {

    @Inject lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        initApplicationComponent()
        initFabric()
    }

    private fun initApplicationComponent() {
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

}