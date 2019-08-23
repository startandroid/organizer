package ru.startandroid.widgetsbase.ui.config.di

import android.app.Activity
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import ru.startandroid.widgetsbase.ui.config.widget.WidgetConfigContainerFragment
import ru.startandroid.widgetsbase.ui.config.WidgetsConfigActivity
import ru.startandroid.widgetsbase.ui.config.list.WidgetsConfigFragment
import ru.startandroid.widgetsbase.ui.config.widget.ARG_WIDGET_ID

@Module()
class WidgetsConfigActivityModule {
    @Provides
    fun provideActivity(activity: WidgetsConfigActivity): Activity = activity
}

@Module()
abstract class WidgetsConfigActivityInjectorModule {
    @ContributesAndroidInjector(modules = [])
    abstract fun contributeWidgetsConfigFragmentInjector(): WidgetsConfigFragment

    @ContributesAndroidInjector(modules = [WidgetConfigContainerFragmentModule::class])
    abstract fun contributeWidgetConfigContainerFragmentInjector(): WidgetConfigContainerFragment
}

@Module
class WidgetConfigContainerFragmentModule() {

    /*

    Incredible, but it works! We can put fragment argument's data into objects that are injected in this fragment.

    1) We create fragment with widgetId in arguments.
        Method WidgetConfigContainerFragment.newInstance(widgetId)

    2) When we put fragment to activity, dagger will do injection into this fragment.
       Because fragment is extending DaggerFragment, that does injecting in onAttach method.

    3) For doing injection Dagger will create subcomponent for this fragment.
        We asked about it here: WidgetsConfigActivityInjectorModule.contributeWidgetConfigContainerFragmentInjector()

    4) This subcomponent by default has instance of WidgetConfigContainerFragment.
        It's implemented somewhere in dagger library, we dont need to do anything for it.

    5) We can extract something from arguments of instance WidgetConfigContainerFragment
        Method provideWidgetId extracts and returns widgetId

    6) Now we can use widgetId for any objects that injected into WidgetConfigContainerFragment
        Dagger can put widgetId into objects constructor.
        Exmaple: WidgetConfigContainerViewModelFactory

    */


    @Provides
    fun provideWidgetId(fragment: WidgetConfigContainerFragment): Int {
        return fragment.arguments?.getInt(ARG_WIDGET_ID) ?: 0
    }

}
