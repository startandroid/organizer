package ru.startandroid.widgetsbase.domain.model

open class WidgetData

data class WidgetDataEntity(
        val id: Int,
        val data: WidgetData
)