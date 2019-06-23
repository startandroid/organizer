package ru.startandroid.widgetsbase.refresh

import ru.startandroid.widgetsbase.WidgetConfig
import ru.startandroid.widgetsbase.WidgetData


interface WidgetDbDataHelper {

    fun initConfig(): WidgetConfig?

    fun correctDataAccordingToConfig(data: WidgetData?, config: WidgetConfig?): WidgetData

    fun refreshData(config: WidgetConfig?): WidgetData?
}