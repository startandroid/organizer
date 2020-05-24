package ru.startandroid.widgetsbase.data.db.model

import androidx.room.*
import ru.startandroid.widgetsbase.data.DB_TABLE_CONFIG
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@Entity(tableName = DB_TABLE_CONFIG.TABLE_NAME)
@TypeConverters(UpdateIntervalDbConverter::class)
data class WidgetConfigEntityDb(
        @PrimaryKey
        @ColumnInfo(name = DB_TABLE_CONFIG.COLUMNS.ID)
        val id: Int,

        @ColumnInfo(name = DB_TABLE_CONFIG.COLUMNS.CONFIG)
        val config: String,

        @ColumnInfo(name = DB_TABLE_CONFIG.COLUMNS.ENABLED)
        val enabled: Boolean,

        @ColumnInfo(name = DB_TABLE_CONFIG.COLUMNS.UPDATE_INTERVAL)
        val updateInterval: UpdateInterval
)

enum class UpdateInterval(val durationInMillis: Long) {
    NONE(0),
    MINUTES_15(TimeUnit.MINUTES.toMillis(15)),
    MINUTES_30(TimeUnit.MINUTES.toMillis(30)),
    HOUR_1(TimeUnit.HOURS.toMillis(1)),
    HOUR_3(TimeUnit.HOURS.toMillis(3)),
    HOUR_6(TimeUnit.HOURS.toMillis(6)),
    HOUR_12(TimeUnit.HOURS.toMillis(12)),
    DAY_1(TimeUnit.DAYS.toMillis(1))
}

class UpdateIntervals @Inject constructor() {

    fun getInterval(index: Int?) =
            UpdateInterval.values().get(index ?: 0)

    fun indexOfInterval(value: UpdateInterval) =
            value.ordinal

}

class UpdateIntervalDbConverter {
    @TypeConverter
    fun fromUpdateInterval(updateInterval: UpdateInterval?): Long? =
            updateInterval?.durationInMillis

    @TypeConverter
    fun toUpdateInterval(value: Long?): UpdateInterval? =
            UpdateInterval.values().asSequence().find { it.durationInMillis == value }
                    ?: UpdateInterval.MINUTES_15
}