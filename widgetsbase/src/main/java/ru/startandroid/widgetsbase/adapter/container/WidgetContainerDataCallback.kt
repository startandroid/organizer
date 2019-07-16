package ru.startandroid.widgetsbase.adapter.container

interface WidgetContainerDataCallback {
    fun getWidgetContainerData(): WidgetContainerData
    fun setWidgetContainerData(widgetContainerData: WidgetContainerData)
}