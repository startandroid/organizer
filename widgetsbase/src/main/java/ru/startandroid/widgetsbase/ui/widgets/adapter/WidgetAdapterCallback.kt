package ru.startandroid.widgetsbase.ui.widgets.adapter


import android.content.Context
import ru.startandroid.device.Navigator
import ru.startandroid.widgetsbase.data.db.refresh.WidgetsRefresher
import javax.inject.Inject

class WidgetAdapterCallback @Inject constructor(
        private val widgetsRefresher: WidgetsRefresher,
        private val navigator: Navigator
) {

    fun onWidgetRefreshClick(id: Int) {
        widgetsRefresher.refresh(id)
    }

    fun onWidgetSettingsClick(id: Int) {
        navigator.openWidgetConfig(id)
    }

    fun onWidgetCloseClick(id: Int) {

    }
}