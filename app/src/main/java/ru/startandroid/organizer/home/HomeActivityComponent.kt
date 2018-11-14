package ru.startandroid.organizer.home

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntKey
import javax.inject.Scope
import dagger.multibindings.StringKey
import dagger.multibindings.IntoMap
import ru.startandroid.organizer.home.widget.widgets.*
import javax.inject.Provider


@Scope
annotation class ScopeHome

@Module()
abstract class HomeActivityModule {

    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    internal abstract fun contributeHomeFragmentInjector(): HomeFragment

}


@Module
class HomeFragmentModule {

    @Provides
    @IntoMap
    @IntKey(WIDGETS_IDS.TEST_WIDGET_1)
    fun provideWidget1Content(): WidgetContent {
        return TestWidget1Content()
    }

    @Provides
    @IntoMap
    @IntKey(WIDGETS_IDS.TEST_WIDGET_2)
    fun provideWidget2Content(): WidgetContent {
        return TestWidget2Content()
    }

}