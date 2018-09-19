package ru.startandroid.organizer.app

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.startandroid.data.AppDatabase

@Module
class DataModule {

    @ScopeApplication
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "database.db").build()
    }


}