package ru.startandroid.organizer.app.di

import dagger.Component
import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.startandroid.domain.ScopeApplication
import ru.startandroid.organizer.app.App
import ru.startandroid.organizer.fortest.TestActivity
import ru.startandroid.organizer.fortest.TestActivityInjectorModule
import ru.startandroid.organizer.home.HomeActivity
import ru.startandroid.organizer.home.di.HomeActivityInjectorModule
import ru.startandroid.organizer.home.di.HomeActivityModule
import ru.startandroid.organizer.home.di.ScopeHome
import ru.startandroid.widgets.WidgetsModule
import ru.startandroid.widgetsbase.di.WidgetsCommonModule

@Component(modules = [AppModule::class,
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

    @ScopeHome
    @ContributesAndroidInjector(modules = [HomeActivityInjectorModule::class, HomeActivityModule::class])
    internal abstract fun contributeHomeActivityInjector(): HomeActivity


    @ContributesAndroidInjector(modules = [TestActivityInjectorModule::class])
    internal abstract fun contributeTestActivityInjector(): TestActivity


}

