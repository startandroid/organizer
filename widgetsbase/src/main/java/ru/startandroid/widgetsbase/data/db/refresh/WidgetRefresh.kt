package ru.startandroid.widgetsbase.data.db.refresh

import ru.startandroid.widgetsbase.domain.model.*

interface WidgetRefresh {

    /**
     * You get config and should return new refreshed data according to new config.
     * Called in the background thread.
     */
    fun refreshData(config: WidgetConfigEntity): WidgetData?

}