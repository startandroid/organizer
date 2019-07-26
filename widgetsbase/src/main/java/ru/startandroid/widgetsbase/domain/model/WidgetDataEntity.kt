package ru.startandroid.widgetsbase.domain.model

interface WidgetData

data class WidgetDataEntity(
        val id: Int,
        val data: WidgetData
)