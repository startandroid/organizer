package ru.startandroid.widgetsbase.data.refresh

import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import ru.startandroid.widgetsbase.domain.model.WidgetData
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity


interface WidgetDbDataHelper {

    fun initConfig(): WidgetConfig?

    fun correctDataAccordingToConfig(data: WidgetDataEntity?, config: WidgetConfigEntity?): WidgetData

    fun refreshData(config: WidgetConfigEntity?): WidgetData?
}