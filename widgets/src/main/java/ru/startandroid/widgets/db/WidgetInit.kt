package ru.startandroid.widgets.db

import ru.startandroid.widgets.db.data.WidgetDataEntityDb

interface WidgetInit {

    fun initRecord(): WidgetDataEntityDb

}