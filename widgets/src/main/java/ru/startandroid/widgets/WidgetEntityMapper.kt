package ru.startandroid.widgets

import android.util.Log
import com.google.gson.Gson
import ru.startandroid.widgets.db.data.WidgetDataEntityDb
import javax.inject.Inject

class WidgetEntityMapper
@Inject
constructor(
        widgetRegistrator: ToMapperRegistrator,
        private val gson: Gson
) {

    interface ToMapperRegistrator {
        fun registerWidgetToMapper(registerFunc: (id: Int, widgetDataCls: Class<out WidgetData>
                                                  //, widgetSettingsCls: Class<out WidgetSettings>?
        ) -> Unit)
    }

    // TODO use KClass
    val dataClasses = mutableMapOf<Int, Class<out WidgetData>>()
    val settingsClasses = mutableMapOf<Int, Class<out WidgetSettings>>()

    init {
        Log.d("qweee", "WidgetEntityMapper init, widgetRegistrator ${widgetRegistrator}")
        widgetRegistrator.registerWidgetToMapper {
            id: Int, widgetDataCls: Class<out WidgetData> ->
            dataClasses.put(id, widgetDataCls)
            //settingsClasses.put(id, widgetSettingsCls)
        }
    }


    fun map(widgetDataEntityDb: WidgetDataEntityDb): WidgetDataEntity<out WidgetData>? {

        val data = dataClasses.get(widgetDataEntityDb.id)?.let { gson.fromJson(widgetDataEntityDb.data, it) }

        if (data == null) return null

        return WidgetDataEntity(widgetDataEntityDb.id, data)
    }

    fun map(widgetDataEntity: WidgetDataEntity<out WidgetData>): WidgetDataEntityDb {
         val data = gson.toJson(widgetDataEntity.data)

        return WidgetDataEntityDb(widgetDataEntity.id, data)
    }

}