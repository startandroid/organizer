package ru.startandroid.widgets

import android.util.Log
import com.google.gson.Gson
import ru.startandroid.widgets.db.data.WidgetDataEntityDb
import javax.inject.Inject
import kotlin.reflect.KClass

class WidgetEntityMapper
@Inject
constructor(
        widgetRegistrator: ToMapperRegistrator,
        private val gson: Gson
) {

    interface ToMapperRegistrator {
        fun registerWidgetToMapper(registerFunc: (id: Int, widgetDataCls: KClass<out WidgetData>
                //, widgetSettingsCls: Class<out WidgetSettings>?
        ) -> Unit)
    }

    val dataClasses = mutableMapOf<Int, KClass<out WidgetData>>()
    val settingsClasses = mutableMapOf<Int, KClass<out WidgetSettings>>()

    init {
        Log.d("qweee", "WidgetEntityMapper init, widgetRegistrator ${widgetRegistrator}")
        widgetRegistrator.registerWidgetToMapper { id: Int, widgetDataCls: KClass<out WidgetData> ->
            dataClasses.put(id, widgetDataCls)
            //settingsClasses.put(id, widgetSettingsCls)
        }
    }


    fun map(widgetDataEntityDb: WidgetDataEntityDb): WidgetDataEntity? {

        val data = dataClasses.get(widgetDataEntityDb.id)?.let { gson.fromJson(widgetDataEntityDb.data, it.java) }

        if (data == null) return null

        return WidgetDataEntity(widgetDataEntityDb.id, data)
    }

    fun map(widgetDataEntity: WidgetDataEntity): WidgetDataEntityDb {
        val data = gson.toJson(widgetDataEntity.data)

        return WidgetDataEntityDb(widgetDataEntity.id, data)
    }
}