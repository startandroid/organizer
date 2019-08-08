package ru.startandroid.widgetsbase.ui.config.di

import android.app.Activity
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import ru.startandroid.widgetsbase.ui.config.WidgetConfigContainerFragment
import ru.startandroid.widgetsbase.ui.config.WidgetsConfigActivity
import ru.startandroid.widgetsbase.ui.config.WidgetsConfigFragment

@Module()
class WidgetsConfigActivityModule {
    @Provides
    fun provideActivity(activity: WidgetsConfigActivity): Activity = activity
}

@Module()
abstract class WidgetsConfigActivityInjectorModule {
    @ContributesAndroidInjector(modules = [])
    abstract fun contributeHomeFragmentInjector(): WidgetsConfigFragment

    @ContributesAndroidInjector(modules = [])
    abstract fun contributeWidgetConfigContainerFragmentInjector(): WidgetConfigContainerFragment
}