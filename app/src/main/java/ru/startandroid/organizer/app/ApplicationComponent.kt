package ru.startandroid.organizer.app

import dagger.Component
import dagger.Module
import javax.inject.Scope
import dagger.android.ContributesAndroidInjector
import ru.startandroid.organizer.home.*


@Scope
annotation class ScopeApplication

@Component(modules = [AppModule::class, DataModule::class, ActivitiesSubcomponentModule::class])
@ScopeApplication
interface ApplicationComponent {
    fun injectApp(app: App)
}


@Module(subcomponents = [])
abstract class ActivitiesSubcomponentModule {

    @ScopeHome
    @ContributesAndroidInjector(modules = [HomeActivityModule::class])
    internal abstract fun contributeHomeActivityInjector(): HomeActivity

}

