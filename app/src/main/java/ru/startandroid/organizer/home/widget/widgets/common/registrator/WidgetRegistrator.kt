package ru.startandroid.organizer.home.widget.widgets.common.registrator

import ru.startandroid.organizer.home.widget.widgets.common.WidgetData
import ru.startandroid.organizer.home.widget.widgets.common.WidgetEntityMapper
import ru.startandroid.organizer.home.widget.widgets.common.WidgetSettings
import ru.startandroid.organizer.home.widget.widgets.common.adapter.WidgetContent
import ru.startandroid.organizer.home.widget.widgets.common.adapter.WidgetProvider
import javax.inject.Provider

interface WidgetRegistrator : WidgetEntityMapper.ToMapperRegistrator, WidgetProvider.ToProviderRegistrator

class WidgetRegistratorImpl(val registerDatas: List<RegisterData>): WidgetRegistrator {


    // TODO do it more easier? data classes or sealed classes?
    interface RegisterData {
        fun id(): Int
        fun widgetDataCls(): Class<out WidgetData>
        fun widgetSettingsCls(): Class<out WidgetSettings>
        fun widgetContentProvider(): Provider<out WidgetContent>
    }

    override fun registerWidgetToMapper(registerFunc: (id: Int, widgetDataCls: Class<out WidgetData>, widgetSettingsCls: Class<out WidgetSettings>) -> Unit) {
        registerDatas.forEach { registerFunc(it.id(), it.widgetDataCls(), it.widgetSettingsCls()) }
    }

    override fun registerWidgetToProvider(registerFunc: (id: Int, widgetContentProvider: Provider<out WidgetContent>) -> Unit) {
        registerDatas.forEach { registerFunc(it.id(), it.widgetContentProvider()) }
    }

}