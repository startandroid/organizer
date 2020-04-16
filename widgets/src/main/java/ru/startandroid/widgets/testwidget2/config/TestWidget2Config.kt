package ru.startandroid.widgets.testwidget2.config

import kotlinx.android.parcel.Parcelize
import ru.startandroid.widgetsbase.domain.model.WidgetConfig

@Parcelize
data class TestWidget2Config(
        val flag1: Boolean,
        val flag2: Boolean
) : WidgetConfig