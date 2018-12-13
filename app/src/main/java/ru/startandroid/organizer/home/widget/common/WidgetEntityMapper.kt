package ru.startandroid.organizer.home.widget.common

import com.google.gson.Gson
import ru.startandroid.domain.WidgetDataEntityDb
import javax.inject.Inject

class WidgetEntityMapper @Inject constructor(
        widgetRegistrator: ToMapperRegistrator,
        private val gson: Gson
) {

    interface ToMapperRegistrator {
        fun registerWidgetToMapper(registerFunc: (id: Int, widgetDataCls: Class<out WidgetData>, widgetSettingsCls: Class<out WidgetSettings>) -> Unit)
    }

    // TODO use KClass
    val dataClasses = mutableMapOf<Int, Class<out WidgetData>>()
    val settingsClasses = mutableMapOf<Int, Class<out WidgetSettings>>()

    init {
        widgetRegistrator.registerWidgetToMapper {
            id: Int, widgetDataCls: Class<out WidgetData>, widgetSettingsCls: Class<out WidgetSettings> ->
            dataClasses.put(id, widgetDataCls)
            settingsClasses.put(id, widgetSettingsCls)
        }
    }


    fun map(widgetDataEntityDb: WidgetDataEntityDb): WidgetDataEntity<out WidgetData>? {

        val data = dataClasses.get(widgetDataEntityDb.id)?.let { gson.fromJson(widgetDataEntityDb.data, it) }
        //val settings = settingsClasses.get(widgetDataEntityDb.id)?.let { gson.fromJson(widgetDataEntityDb.settings, it) }

        if (data == null) return null

        return WidgetDataEntity(widgetDataEntityDb.id, data)

    }

}