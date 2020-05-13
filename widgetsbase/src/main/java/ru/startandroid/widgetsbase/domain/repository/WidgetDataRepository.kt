package ru.startandroid.widgetsbase.domain.repository

import io.reactivex.Flowable
import ru.startandroid.widgetsbase.domain.model.WidgetData
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity

interface WidgetDataRepository {

    fun getEnabledWidgets(): Flowable<List<WidgetDataEntity>>

    fun getWidgetByIdSync(id: Int): WidgetDataEntity

    fun updateOrInsertSync(id: Int, widgetData: WidgetData): Long

}