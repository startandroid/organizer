package ru.startandroid.organizer.app

import dagger.Component
import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.startandroid.organizer.TestActivity
import ru.startandroid.organizer.home.HomeActivity
import ru.startandroid.organizer.home.HomeActivityInjectorModule
import ru.startandroid.organizer.home.HomeActivityModule
import ru.startandroid.organizer.home.ScopeHome
import ru.startandroid.organizer.home.widget.common.registrator.WidgetRegistratorModule
import ru.startandroid.organizer.home.widget.common.registrator.WidgetsModule
import javax.inject.Scope


@Scope
annotation class ScopeApplication

@Component(modules = [AppModule::class, DataModule::class, SubcomponentModule::class, WidgetRegistratorModule::class, WidgetsModule::class])
@ScopeApplication
interface ApplicationComponent {
    fun injectApp(app: App)
}


@Module(subcomponents = [])
abstract class SubcomponentModule {

    @ScopeHome
    @ContributesAndroidInjector(modules = [HomeActivityInjectorModule::class, HomeActivityModule::class])
    internal abstract fun contributeHomeActivityInjector(): HomeActivity


    @ContributesAndroidInjector()
    internal abstract fun contributeTestActivityInjector(): TestActivity


}

