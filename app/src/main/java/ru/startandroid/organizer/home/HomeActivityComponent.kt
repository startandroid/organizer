package ru.startandroid.organizer.home

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import ru.startandroid.organizer.app.NetworkModule
import javax.inject.Scope


@Scope
annotation class ScopeHome

@Module()
abstract class HomeActivityInjectorModule {
    @ContributesAndroidInjector(modules = [HomeFragmentModule::class, NetworkModule::class])
    internal abstract fun contributeHomeFragmentInjector(): HomeFragment
}


@Module()
class HomeActivityModule {

//    @ScopeHome
//    @Provides
//    fun provideUiNavigator(homeActivity: HomeActivity): DeepLinkNavigator {
//        return DeepLinkNavigator(homeActivity)
//    }
}


@Module
class HomeFragmentModule {

}


