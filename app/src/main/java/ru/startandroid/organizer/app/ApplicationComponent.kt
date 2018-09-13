package ru.startandroid.organizer.app

import android.app.Activity
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import ru.startandroid.organizer.home.HomeActivity
import ru.startandroid.organizer.home.HomeActivitySubcomponent

@Component(modules = [ActivitiesSubcomponentModule::class])
interface ApplicationComponent {
    fun injectApp(app: App)
}


@Module(subcomponents = [HomeActivitySubcomponent::class])
abstract class ActivitiesSubcomponentModule {

    @Binds
    @IntoMap
    @ActivityKey(HomeActivity::class)
    abstract fun bindHomeActivityInjectorFactory(builder: HomeActivitySubcomponent.Builder): AndroidInjector.Factory<out Activity>

}

