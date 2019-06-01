package ru.startandroid.widgets.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.startandroid.widgets.refresh.WidgetsRefresher
import javax.inject.Inject

class WidgetDbInitializer @Inject constructor(val widgetRegistrator: ToDbInitializerRegistrator, val widgetsRefresher: WidgetsRefresher) {

    interface ToDbInitializerRegistrator {
        fun registerWidgetToDbInitializer(registerFunc: (id: Int) -> Unit)
    }

    lateinit var widgetDatabase: WidgetDatabase

    fun createDatabase(context: Context): WidgetDatabase {
        widgetDatabase = Room.databaseBuilder(context, WidgetDatabase::class.java, "widget_database.db")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        createInitRecords()
                    }
                })
                .build()
        return widgetDatabase
    }

    private fun createInitRecords() {

        val ids = mutableListOf<Int>()
        widgetRegistrator.registerWidgetToDbInitializer { id ->
            ids.add(id)
        }
        widgetsRefresher.init(ids.toIntArray())
    }

}