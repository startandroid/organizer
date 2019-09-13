package ru.startandroid.widgetsbase.domain.repository

interface WidgetWorkManager {

    fun init(ids: IntArray)

    fun refresh(id: Int)

    fun refreshThenSchedule(id: Int)

    fun correctThenRefreshThenSchedule(id: Int)

    fun startPeriodicRefresh(id: Int, updateIntervalInMillis: Long)

    fun stopPeriodicRefresh(id: Int)
}