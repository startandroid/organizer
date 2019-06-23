package ru.startandroid.widgetsbase


interface WidgetData

interface WidgetConfig

data class WidgetDataEntity(
        val id: Int,
        val data: WidgetData
)

data class WidgetConfigEntity(
        val id: Int,
        val config: WidgetConfig,
        val enabled: Boolean = true
)






