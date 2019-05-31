package ru.startandroid.widgets.refresh

import ru.startandroid.widgets.WidgetConfig
import ru.startandroid.widgets.WidgetData

interface WidgetRefresher {

    fun initConfig(): WidgetConfig?

    fun correctDataAccordingToConfig(data: WidgetData?, config: WidgetConfig?): WidgetData

    fun refreshData(config: WidgetConfig?): WidgetData?
}