package ru.startandroid.widgets.db

import ru.startandroid.widgets.db.data.WidgetConfigEntityDb
import ru.startandroid.widgets.db.data.WidgetDataEntityDb

interface WidgetInit {

    fun initData(): WidgetDataEntityDb

    fun initConfig(): WidgetConfigEntityDb
}