package ru.startandroid.organizer.home

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = [HomeActivityModule::class])
interface HomeActivitySubcomponent: AndroidInjector<HomeActivity> {

    @Subcomponent.Builder
    abstract class Builder: AndroidInjector.Builder<HomeActivity>() {

    }

}

@Module()
class HomeActivityModule {


}
