package ru.startandroid.organizer.home.widget.common.adapter

interface WidgetAdapterCallback  {
    fun onWidgetRefreshClick(id: Int)
    fun onWidgetSettingsClick(id: Int)
    fun onWidgetCloseClick(id: Int)
}