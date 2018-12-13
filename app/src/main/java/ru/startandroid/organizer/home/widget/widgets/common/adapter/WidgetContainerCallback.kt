package ru.startandroid.organizer.home.widget.widgets.common.adapter

interface WidgetContainerCallback {
    fun getHeader(): WidgetContainerHeader
    fun setHeader(widgetHeader: WidgetContainerHeader)
}