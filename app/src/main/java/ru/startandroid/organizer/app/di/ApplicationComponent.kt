package ru.startandroid.organizer.app.di

import dagger.Component
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ru.startandroid.domain.ScopeActivity
import ru.startandroid.domain.ScopeApplication
import ru.startandroid.organizer.app.App
import ru.startandroid.organizer.fortest.TestActivity
import ru.startandroid.organizer.fortest.TestActivityInjectorModule
import ru.startandroid.organizer.fortest.TestActivityModule
import ru.startandroid.organizer.home.HomeActivity
import ru.startandroid.organizer.home.di.HomeActivityInjectorModule
import ru.startandroid.organizer.home.di.HomeActivityModule
import ru.startandroid.widgets.WidgetsModule
import ru.startandroid.widgetsbase.di.WidgetsCommonModule
import ru.startandroid.widgetsbase.ui.config.WidgetsConfigActivity
import ru.startandroid.widgetsbase.ui.config.di.WidgetsConfigActivityInjectorModule
import ru.startandroid.widgetsbase.ui.config.di.WidgetsConfigActivityModule

@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    SubcomponentModule::class,
    WidgetsCommonModule::class,
    WidgetsModule::class
])
@ScopeApplication
interface ApplicationComponent {
    fun injectApp(app: App)
}


@Module(subcomponents = [])
abstract class SubcomponentModule {

    @ScopeActivity
    @ContributesAndroidInjector(modules = [HomeActivityInjectorModule::class, HomeActivityModule::class])
    abstract fun contributeHomeActivityInjector(): HomeActivity

    @ScopeActivity
    @ContributesAndroidInjector(modules = [TestActivityInjectorModule::class, TestActivityModule::class])
    abstract fun contributeTestActivityInjector(): TestActivity

    @ScopeActivity
    @ContributesAndroidInjector(modules = [WidgetsConfigActivityInjectorModule::class, WidgetsConfigActivityModule::class])
    abstract fun contributeWidgetsConfigActivityInjector(): WidgetsConfigActivity


}

