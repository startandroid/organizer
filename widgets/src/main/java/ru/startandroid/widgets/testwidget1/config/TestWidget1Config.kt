package ru.startandroid.widgets.testwidget1.config

import kotlinx.android.parcel.Parcelize
import ru.startandroid.widgetsbase.domain.model.WidgetConfig

@Parcelize
data class TestWidget1Config(
        val flag: Boolean,
        val text: String,
        val list: List<String>
) : WidgetConfig