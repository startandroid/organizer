package ru.startandroid.widgetsbase.data.db.refresh

import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import ru.startandroid.widgetsbase.domain.model.WidgetData
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity


interface WidgetDbDataHelper {

    fun getInitConfig(): WidgetConfigEntity?

    fun correctDataAccordingToConfig(data: WidgetDataEntity?, config: WidgetConfigEntity?): WidgetData

    fun refreshData(config: WidgetConfigEntity?): WidgetData?
}