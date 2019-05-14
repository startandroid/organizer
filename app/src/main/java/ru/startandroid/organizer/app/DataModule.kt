package ru.startandroid.organizer.app

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import ru.startandroid.data.AppDatabase
import ru.startandroid.domain.WidgetDataEntityDb
import ru.startandroid.domain.WidgetSettingsEntityDb
import ru.startandroid.organizer.home.widget.widgets.*
import ru.startandroid.organizer.home.widget.common.WIDGETS_IDS
import java.util.concurrent.Executors

@Module
class DataModule {

    lateinit var appDatabase: AppDatabase

    @ScopeApplication
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
         appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "database.db")
                .addCallback(object: RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Log.d("qweee", "room onCreate")
                        Executors.newSingleThreadExecutor().execute { // TODO use DB scheduler
                            createInitRecords()
                        }

                    }
                })
                .allowMainThreadQueries() // TODO disable
                .build()
        return appDatabase
    }

    private fun createInitRecords() {
        val gson = Gson() // TODO inject it

        val wdata1 = TestWidget1Data("test")
        val entityDb1 = WidgetDataEntityDb(WIDGETS_IDS.TEST_WIDGET_1,
                gson.toJson(wdata1))

        val wsett1 = TestWidget1Settings(true)
        val entitySettingsDb1 = WidgetSettingsEntityDb(WIDGETS_IDS.TEST_WIDGET_1,
                gson.toJson(wsett1), 1, true)


        val wdata2 = TestWidget2Data("test1", "test2")
        val entityDb2 = WidgetDataEntityDb(WIDGETS_IDS.TEST_WIDGET_2,
                gson.toJson(wdata2))

        val wsett2 = TestWidget2Settings(true, false)
        val entitySettingsDb2 = WidgetSettingsEntityDb(WIDGETS_IDS.TEST_WIDGET_2,
                gson.toJson(wsett2), 2, true)

        val wdata3 = WeatherWidgetData("weather1", "weather2")
        val entityDb3 = WidgetDataEntityDb(WIDGETS_IDS.TEST_WIDGET_3,
                gson.toJson(wdata3))

        val wsett3 = WeatherWidgetSettings(true, false)
        val entitySettingsDb3 = WidgetSettingsEntityDb(WIDGETS_IDS.TEST_WIDGET_3,
                gson.toJson(wsett3), 3, true)


        appDatabase.widgetDao().insertAll(listOf(entityDb1, entityDb2, entityDb3))
        appDatabase.widgetSettingsDao().insertAll(listOf(entitySettingsDb1, entitySettingsDb2, entitySettingsDb3))

    }


}