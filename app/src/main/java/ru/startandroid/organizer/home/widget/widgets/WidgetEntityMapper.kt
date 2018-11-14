package ru.startandroid.organizer.home.widget.widgets

import com.google.gson.Gson
import ru.startandroid.domain.WidgetDataEntityDb
import javax.inject.Inject

class WidgetEntityMapper @Inject constructor(
        private val gson: Gson
) {

    // TODO use KClass
    val dataClasses = mutableMapOf<Int, Class<out WidgetData>>()
    val settingsClasses = mutableMapOf<Int, Class<out WidgetSettings>>()

    init {
        registerWidget(WIDGETS_IDS.TEST_WIDGET_1, TestWidget1Data::class.java, TestWidget1Settings::class.java)
        registerWidget(WIDGETS_IDS.TEST_WIDGET_2, TestWidget2Data::class.java, TestWidget2Settings::class.java)
    }

    private fun registerWidget(id: Int, widgetDataCls: Class<out WidgetData>, widgetSettingsCls: Class<out WidgetSettings>) {
        dataClasses.put(id, widgetDataCls)
        settingsClasses.put(id, widgetSettingsCls)
    }

    fun map(widgetDataEntityDb: WidgetDataEntityDb): WidgetDataEntity<out WidgetData>? {

        val data = dataClasses.get(widgetDataEntityDb.id)?.let { gson.fromJson(widgetDataEntityDb.data, it) }
        //val settings = settingsClasses.get(widgetDataEntityDb.id)?.let { gson.fromJson(widgetDataEntityDb.settings, it) }

        if (data == null) return null

        return WidgetDataEntity(widgetDataEntityDb.id, data)

    }

}