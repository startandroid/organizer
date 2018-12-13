package ru.startandroid.organizer.home.widget.common.adapter

interface WidgetContainerCallback {
    fun getHeader(): WidgetContainerHeader
    fun setHeader(widgetHeader: WidgetContainerHeader)
}