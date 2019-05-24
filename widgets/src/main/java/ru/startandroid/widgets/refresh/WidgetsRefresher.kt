package ru.startandroid.widgets.refresh

import javax.inject.Inject
import javax.inject.Provider


class WidgetsRefresher @Inject constructor(widgetRegistrator: ToRefresherRegistrator) {

    interface ToRefresherRegistrator {
        fun registerWidgetToRefresher(registerFunc: (id: Int, widgetRefresher: Provider<out WidgetRefresher>) -> Unit)
    }

    private val widgets = mutableMapOf<Int, Provider<out WidgetRefresher>>()

    init {
        widgetRegistrator.registerWidgetToRefresher { id, widgetRefresher -> widgets.put(id, widgetRefresher) }
    }

    fun refresh(id: Int?) {
        widgets.get(id)?.get()?.refresh()
    }

}