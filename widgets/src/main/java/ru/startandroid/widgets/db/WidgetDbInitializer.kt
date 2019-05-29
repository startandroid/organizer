package ru.startandroid.widgets.db

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.startandroid.widgets.db.data.WidgetConfigEntityDb
import ru.startandroid.widgets.db.data.WidgetDataEntityDb
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Provider

class WidgetDbInitializer @Inject constructor(val widgetRegistrator: ToDbInitializerRegistrator) {

    interface ToDbInitializerRegistrator {
        fun registerWidgetToDbInitializer(registerFunc: (id: Int, widgetInitProvider: Provider<out WidgetInit>) -> Unit)
    }

    lateinit var widgetDatabase: WidgetDatabase

    fun createDatabase(context: Context): WidgetDatabase {
        widgetDatabase = Room.databaseBuilder(context, WidgetDatabase::class.java, "widget_database.db")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Log.d("qweee", "room onCreate")
                        Executors.newSingleThreadExecutor().execute {
                            createInitRecords()
                        }

                    }
                })
                .build()
        return widgetDatabase
    }

    private fun createInitRecords() {
        val widgetsData = mutableListOf<WidgetDataEntityDb>()
        val widgetsConfig = mutableListOf<WidgetConfigEntityDb>()
        widgetRegistrator.registerWidgetToDbInitializer { id, widgetInitProvider ->
            val widgetInitProvider = widgetInitProvider.get()
            widgetsData.add(widgetInitProvider.initData())
            widgetsConfig.add(widgetInitProvider.initConfig())
        }

        widgetDatabase.widgetDataDao().insert(widgetsData)
        widgetDatabase.widgetConfigDao().insert(widgetsConfig)

    }

}