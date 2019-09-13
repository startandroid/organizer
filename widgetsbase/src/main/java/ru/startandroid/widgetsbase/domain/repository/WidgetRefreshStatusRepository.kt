package ru.startandroid.widgetsbase.domain.repository

interface WidgetRefreshStatusRepository {

    fun initSync(widgetId: Int): Long

    fun acquireRefreshSync(widgetId: Int): Boolean

    fun closeRefreshSync(widgetId: Int): Boolean

}