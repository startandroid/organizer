package ru.startandroid.organizer.home

import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Scope

@Scope
annotation class ScopeHome

@Module()
abstract class HomeActivityModule {

    @ContributesAndroidInjector(modules = [])
    internal abstract fun contributeHomeFragmentInjector(): HomeFragment

}
