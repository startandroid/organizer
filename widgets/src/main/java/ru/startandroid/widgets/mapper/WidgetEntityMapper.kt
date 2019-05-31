package ru.startandroid.widgets.mapper

import com.google.gson.Gson
import ru.startandroid.domain.ScopeApplication
import ru.startandroid.widgets.WidgetConfig
import ru.startandroid.widgets.WidgetConfigEntity
import ru.startandroid.widgets.WidgetData
import ru.startandroid.widgets.WidgetDataEntity
import ru.startandroid.widgets.db.data.WidgetConfigEntityDb
import ru.startandroid.widgets.db.data.WidgetDataEntityDb
import javax.inject.Inject
import kotlin.reflect.KClass

@ScopeApplication
class WidgetEntityMapper
@Inject
constructor(
        val widgetRegistrator: ToMapperRegistrator,
        private val gson: Gson
) {

    interface ToMapperRegistrator {
        fun registerWidgetToMapper(registerFunc: (id: Int,
                                                  widgetDataCls: KClass<out WidgetData>,
                                                  widgetConfigCls: KClass<out WidgetConfig>) -> Unit)
    }

    val dataClasses = mutableMapOf<Int, KClass<out WidgetData>>()
    val configClasses = mutableMapOf<Int, KClass<out WidgetConfig>>()

    init {
        reg()
    }

    private fun reg() {
        // temporary workaround, will be fixed
        if (configClasses.size == 0)
            widgetRegistrator.registerWidgetToMapper { id: Int,
                                                       widgetDataCls: KClass<out WidgetData>,
                                                       widgetConfigCls: KClass<out WidgetConfig> ->
                dataClasses.put(id, widgetDataCls)
                configClasses.put(id, widgetConfigCls)
            }
    }


    fun map(widgetDataEntityDb: WidgetDataEntityDb?): WidgetDataEntity? {
        reg()
        if (widgetDataEntityDb == null) return null
        val data = dataClasses.get(widgetDataEntityDb.id)?.let { gson.fromJson(widgetDataEntityDb.data, it.java) }

        if (data == null) return null

        return WidgetDataEntity(widgetDataEntityDb.id, data)
    }

    fun map(widgetDataEntity: WidgetDataEntity): WidgetDataEntityDb {
        reg()
        val data = gson.toJson(widgetDataEntity.data)

        return WidgetDataEntityDb(widgetDataEntity.id, data)
    }

    fun map(widgetConfigEntityDb: WidgetConfigEntityDb): WidgetConfigEntity? {
        reg()
        val config = configClasses.get(widgetConfigEntityDb.id)?.let { gson.fromJson(widgetConfigEntityDb.config, it.java) }

        if (config == null) return null

        return WidgetConfigEntity(widgetConfigEntityDb.id, config, widgetConfigEntityDb.enabled)
    }

    fun map(widgetConfigEntity: WidgetConfigEntity): WidgetConfigEntityDb {
        reg()
        val config = gson.toJson(widgetConfigEntity.config)

        return WidgetConfigEntityDb(widgetConfigEntity.id, config, widgetConfigEntity.enabled)
    }
}