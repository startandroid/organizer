package ru.startandroid.organizer.home.widget.common

object WIDGETS_IDS {
    const val TEST_WIDGET_1 = 1
    const val TEST_WIDGET_2 = 2
}

interface WidgetData

interface WidgetSettings

// TODO create WidgetDataEntity<out WidgetData> and use it everywhere
data class WidgetDataEntity<D : WidgetData>(
        val id: Int,
        val data: D
)






