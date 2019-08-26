package ru.startandroid.widgetsbase.ui.widgets.adapter


import ru.startandroid.device.Navigator
import ru.startandroid.device.analytics.Analytics
import ru.startandroid.device.analytics.WidgetRefreshEvent
import ru.startandroid.widgetsbase.data.db.refresh.WidgetsRefresher
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository
import javax.inject.Inject

class WidgetAdapterCallback @Inject constructor(
        private val widgetsRefresher: WidgetsRefresher,
        private val navigator: Navigator,
        private val analytics: Analytics,
        private val widgetConfigRepository: WidgetConfigRepository
) {

    fun onWidgetRefreshClick(id: Int) {
        widgetsRefresher.refresh(id)
        analytics.logEvent(WidgetRefreshEvent(id))
    }

    fun onWidgetSettingsClick(id: Int) {
        navigator.openWidgetConfig(id)
    }

    fun onWidgetCloseClick(id: Int) {
        widgetConfigRepository.setEnabled(id, false).subscribe()
    }
}