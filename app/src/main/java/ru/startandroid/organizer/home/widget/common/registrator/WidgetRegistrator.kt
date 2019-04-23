package ru.startandroid.organizer.home.widget.common.registrator

import ru.startandroid.organizer.home.widget.common.WidgetData
import ru.startandroid.organizer.home.widget.common.WidgetEntityMapper
import ru.startandroid.organizer.home.widget.common.WidgetSettings
import ru.startandroid.organizer.home.widget.common.adapter.WidgetContent
import ru.startandroid.organizer.home.widget.common.adapter.WidgetProvider
import ru.startandroid.organizer.home.widget.refresh.WidgetRefresher
import ru.startandroid.organizer.home.widget.refresh.WidgetsRefresher
import javax.inject.Provider

interface WidgetRegistrator :
        WidgetEntityMapper.ToMapperRegistrator,
        WidgetProvider.ToProviderRegistrator,
        WidgetsRefresher.ToRefresherRegistrator

class WidgetRegistratorImpl(val registerDatas: List<RegisterData>): WidgetRegistrator {

    // TODO do it easier? data classes or sealed classes?
    interface RegisterData {
        fun id(): Int
        fun widgetDataCls(): Class<out WidgetData>
        fun widgetSettingsCls(): Class<out WidgetSettings>
        fun widgetContentProvider(): Provider<out WidgetContent>
        fun widgetRefresher(): Provider<out WidgetRefresher>
    }

    override fun registerWidgetToMapper(registerFunc: (id: Int, widgetDataCls: Class<out WidgetData>, widgetSettingsCls: Class<out WidgetSettings>) -> Unit) {
        registerDatas.forEach { registerFunc(it.id(), it.widgetDataCls(), it.widgetSettingsCls()) }
    }

    override fun registerWidgetToProvider(registerFunc: (id: Int, widgetContentProvider: Provider<out WidgetContent>) -> Unit) {
        registerDatas.forEach { registerFunc(it.id(), it.widgetContentProvider()) }
    }

    override fun registerWidgetToRefresher(registerFunc: (id: Int, widgetRefresher: Provider<out WidgetRefresher>) -> Unit) {
        registerDatas.forEach { registerFunc(it.id(), it.widgetRefresher()) }
    }

}