package ru.startandroid.organizer.home

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.startandroid.organizer.app.WeatherNetworkModule
import javax.inject.Scope


@Scope
annotation class ScopeHome

@Module()
abstract class HomeActivityModule {

    @ContributesAndroidInjector(modules = [HomeFragmentModule::class, WeatherNetworkModule::class])
    internal abstract fun contributeHomeFragmentInjector(): HomeFragment

}


@Module
class HomeFragmentModule {

}


