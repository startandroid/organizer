package ru.startandroid.organizer.app.di

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import ru.startandroid.domain.ScopeApplication

@Module
class AppModule(val context: Context) {

    @Provides
    fun provideContext(): Context = context

    @ScopeApplication
    @Provides
    fun provideGson(): Gson = Gson()


}
