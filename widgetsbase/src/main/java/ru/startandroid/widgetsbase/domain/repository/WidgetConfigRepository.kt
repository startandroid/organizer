package ru.startandroid.widgetsbase.domain.repository

import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity

interface WidgetConfigRepository {

    fun getWidgetByIdSync(id: Int): WidgetConfigEntity?

}