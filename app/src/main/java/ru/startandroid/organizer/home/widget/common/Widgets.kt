package ru.startandroid.organizer.home.widget.common

import ru.startandroid.organizer.BuildConfig

object WIDGETS_IDS {
    const val TEST_WIDGET_1 = 1
    const val TEST_WIDGET_2 = 2

    // TODO move to another place
    val ACTION_REFRESH = "${BuildConfig.APPLICATION_ID}.REFRESH"
}



interface WidgetData

interface WidgetSettings

// TODO create WidgetDataEntity<out WidgetData> and use it everywhere
data class WidgetDataEntity<D : WidgetData>(
        val id: Int,
        val data: D
)






