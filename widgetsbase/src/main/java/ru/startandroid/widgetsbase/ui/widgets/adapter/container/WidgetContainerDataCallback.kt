package ru.startandroid.widgetsbase.ui.widgets.adapter.container

interface WidgetContainerDataCallback {
    fun updateWidgetContainerData(func: (oldData: WidgetContainerData) -> WidgetContainerData)
}