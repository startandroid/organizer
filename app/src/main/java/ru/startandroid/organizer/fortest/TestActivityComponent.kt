package ru.startandroid.organizer.fortest

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.startandroid.widgetsbase.config.WidgetConfigContainerFragment


@Module
abstract class TestActivityInjectorModule {
    @ContributesAndroidInjector(modules = [])
    internal abstract fun contributeWidgetConfigContainerFragmentInjector(): WidgetConfigContainerFragment

}

