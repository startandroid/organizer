package ru.startandroid.widgetsbase.data.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.startandroid.widgetsbase.data.metadata.WidgetDbInitMetadataRepository
import ru.startandroid.widgetsbase.data.db.refresh.WidgetsRefresher
import javax.inject.Inject

class WidgetDbInitializer @Inject constructor(
        val widgetMetadataRepository: WidgetDbInitMetadataRepository,
        val widgetsRefresher: WidgetsRefresher) {


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

    private fun createInitRecords() =
            widgetsRefresher.init(widgetMetadataRepository.getWidgetIds())


}