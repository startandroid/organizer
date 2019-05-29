package ru.startandroid.widgets.adapter

import ru.startandroid.widgets.refresh.WidgetsRefresher
import javax.inject.Inject

class WidgetAdapterCallback @Inject constructor(val widgetsRefresher: WidgetsRefresher) {
    fun onWidgetRefreshClick(id: Int) {
        widgetsRefresher.refresh(id)
    }

    fun onWidgetConfigClick(id: Int) {

    }

    fun onWidgetCloseClick(id: Int) {

    }
}