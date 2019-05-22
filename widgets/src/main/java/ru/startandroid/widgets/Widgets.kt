package ru.startandroid.widgets


interface WidgetData

interface WidgetSettings

// TODO create WidgetDataEntity<out WidgetData> and use it everywhere
data class WidgetDataEntity<D : WidgetData>(
        val id: Int,
        val data: D
)






