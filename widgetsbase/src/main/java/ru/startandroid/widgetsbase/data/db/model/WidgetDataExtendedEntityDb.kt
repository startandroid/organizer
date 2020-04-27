package ru.startandroid.widgetsbase.data.db.model

import androidx.room.Embedded

data class WidgetDataExtendedEntityDb(
        @Embedded
        val widgetDataEntityDb: WidgetDataEntityDb,

        val refreshStatus: Int
)