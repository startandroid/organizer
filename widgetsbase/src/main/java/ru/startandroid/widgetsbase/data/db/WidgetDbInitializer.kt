package ru.startandroid.widgetsbase.data.db

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.startandroid.widgetsbase.data.metadata.WidgetDbInitMetadataRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetWorkManager
import java.util.concurrent.Executor
import javax.inject.Inject

class WidgetDbInitializer @Inject constructor(
        val widgetMetadataRepository: WidgetDbInitMetadataRepository,
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
                        createInitRecords()
                    }
                })
                .build()
        return widgetDatabase
    }

    private fun createInitRecords() =
            widgetWorkManager.init(widgetMetadataRepository.getWidgetIds())


}