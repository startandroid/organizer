package ru.startandroid.widgetsbase.domain.repository

import androidx.lifecycle.LiveData
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity

interface WidgetDataRepository {

    fun getEnabledWidgets(): LiveData<List<WidgetDataEntity>>

    fun getWidgetByIdSync(id: Int): WidgetDataEntity?

    fun updateOrInsertSync(widgetDataEntity: WidgetDataEntity): Long
}