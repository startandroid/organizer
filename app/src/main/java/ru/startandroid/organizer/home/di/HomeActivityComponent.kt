package ru.startandroid.organizer.home.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.startandroid.data.network.di.NetworkModule
import ru.startandroid.widgetsbase.ui.widgets.WidgetsFragment
import javax.inject.Scope


@Scope
annotation class ScopeHome

@Module()
abstract class HomeActivityInjectorModule {
    @ContributesAndroidInjector(modules = [HomeFragmentModule::class, NetworkModule::class])
    internal abstract fun contributeHomeFragmentInjector(): WidgetsFragment
}


@Module()
class HomeActivityModule


@Module
class HomeFragmentModule


