package ru.startandroid.widgets.adapter.container

interface WidgetContainerDataCallback {
    fun getWidgetContainerData(): WidgetContainerData
    fun setWidgetContainerData(widgetContainerData: WidgetContainerData)
}