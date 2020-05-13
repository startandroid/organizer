package ru.startandroid.widgetsbase.domain.model

open class WidgetData

data class WidgetDataEntity(
        val id: Int,
        val data: WidgetData,
        val refreshStatus: Int = ru.startandroid.widgetsbase.data.DB_MAPPING.REFRESH_STATUS.DONE
)