package ru.startandroid.organizer.home.di

import android.app.Activity
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import ru.startandroid.data.network.di.NetworkModule
import ru.startandroid.organizer.home.HomeActivity
import ru.startandroid.widgetsbase.ui.widgets.WidgetsFragment


@Module()
abstract class HomeActivityInjectorModule {
    @ContributesAndroidInjector(modules = [HomeFragmentModule::class, NetworkModule::class])
    abstract fun contributeHomeFragmentInjector(): WidgetsFragment
}


@Module()
class HomeActivityModule {
    @Provides
    fun provideActivity(activity: HomeActivity): Activity = activity
}


@Module
class HomeFragmentModule


