package ru.startandroid.organizer.home.widget.common.adapter

interface WidgetContainerDataCallback {
    fun getWidgetContainerData(): WidgetContainerData
    fun setWidgetContainerData(widgetContainerData: WidgetContainerData)
}