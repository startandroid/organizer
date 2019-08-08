package ru.startandroid.organizer.fortest

import android.app.Activity
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector


@Module
abstract class TestActivityInjectorModule {
    @ContributesAndroidInjector(modules = [])
    internal abstract fun contributeTestFragmentInjector(): TestFragment

}

@Module()
class TestActivityModule {
    @Provides
    fun provideActivity(activity: TestActivity): Activity = activity
}

