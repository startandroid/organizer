package ru.startandroid.widgetsbase.ui.widgets.adapter


import ru.startandroid.device.Navigator
import ru.startandroid.device.analytics.Analytics
import ru.startandroid.device.analytics.WidgetRefreshEvent
import ru.startandroid.widgetsbase.domain.repository.WidgetWorkManager
import ru.startandroid.widgetsbase.domain.usecase.DisableWidgetUseCase
import javax.inject.Inject

class WidgetAdapterCallback @Inject constructor(
        private val widgetWorkManager: WidgetWorkManager,
        private val navigator: Navigator,
        private val analytics: Analytics,
        private val disableWidgetUseCase: DisableWidgetUseCase
) {

    fun onWidgetRefreshClick(id: Int) {
        widgetWorkManager.refresh(id)
        analytics.logEvent(WidgetRefreshEvent(id))
    }

    fun onWidgetSettingsClick(id: Int) {
        navigator.openWidgetConfig(id)
    }

    fun onWidgetCloseClick(id: Int) {
        disableWidgetUseCase.invoke(id)
    }
}