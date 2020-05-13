package ru.startandroid.widgetsbase.data.db.refresh

import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.domain.model.WidgetData

interface WidgetRefresh {

    // TODOL try to use Generics here
    /**
     * You get current data and config and should return new refreshed data according to new config.
     * Called in the background thread.
     */
    fun refreshData(currentWidgetData: WidgetData, widgetConfig: WidgetConfig): WidgetData?

}