package ru.startandroid.organizer.home

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import javax.inject.Scope


@Scope
annotation class ScopeHome

@Module()
abstract class HomeActivityInjectorModule {
    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    internal abstract fun contributeHomeFragmentInjector(): HomeFragment
}

@Module()
class HomeActivityModule {

    @ScopeHome
    @Provides
    fun provideUiNavigator(homeActivity: HomeActivity): UINavigator {
        return UINavigator(homeActivity)
    }
}


@Module
class HomeFragmentModule {

}


