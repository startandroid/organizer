package ru.startandroid.device.di

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import ru.startandroid.domain.ScopeApplication

@Module
class DeviceModule {

    @ScopeApplication
    @Provides
    fun provideFirebaseAnalytics(context: Context) = FirebaseAnalytics.getInstance(context)

}