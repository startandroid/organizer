package ru.startandroid.widgetsbase.data.db.correct

import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.domain.model.WidgetData

interface WidgetCorrect {

    // TODOL try to use Generics here
    /**
     *  You get current data and should return new data that is corrected by you according to config.
     *  The method is called after config was updated on config screen. It might be helpful to make data valid even if refresh cannot be done for some reasons (no internet).
     *
     * Called in the background thread.
     * Don't use it to refresh data. There is method refreshData for this purpose.
     */
    fun correctDataAccordingToConfig(currentWidgetData: WidgetData, widgetConfig: WidgetConfig): WidgetData

}