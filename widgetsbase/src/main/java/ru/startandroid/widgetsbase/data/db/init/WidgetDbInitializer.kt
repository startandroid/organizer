package ru.startandroid.widgetsbase.data.db.init

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.startandroid.widgetsbase.data.db.WidgetDatabase
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadataRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetWorkManager
import java.util.concurrent.Executor
import javax.inject.Inject


// TODOL refactor it
// add to make new widgets able to be added when app is updated
class WidgetDbInitializer @Inject constructor(
        val widgetMetadataRepository: WidgetMetadataRepository,
        val widgetWorkManager: WidgetWorkManager,
        val dbExecutor: Executor) {

    lateinit var widgetDatabase: WidgetDatabase

    fun createDatabase(context: Context): WidgetDatabase {
        Log.d("qweee", "WidgetDbInitializer createDatabase")
        widgetDatabase = Room.databaseBuilder(context, WidgetDatabase::class.java, "widget_database.db")
                .setQueryExecutor(dbExecutor)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Log.d("qweee", "WidgetDbInitializer createInitRecords")
                        widgetWorkManager.init(widgetMetadataRepository.getWidgetIds())
                    }
                })
                .build()
        return widgetDatabase
    }

}