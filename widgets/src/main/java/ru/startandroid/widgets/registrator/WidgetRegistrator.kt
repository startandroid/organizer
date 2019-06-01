package ru.startandroid.widgets.registrator

import ru.startandroid.domain.ScopeApplication
import ru.startandroid.widgets.WidgetConfig
import ru.startandroid.widgets.WidgetData
import ru.startandroid.widgets.adapter.content.WidgetContent
import ru.startandroid.widgets.adapter.content.WidgetProvider
import ru.startandroid.widgets.db.WidgetDbInitializer
import ru.startandroid.widgets.mapper.WidgetEntityMapper
import ru.startandroid.widgets.refresh.WidgetRefresher
import ru.startandroid.widgets.refresh.WidgetWorkerFactory
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

interface WidgetRegistrator :
        WidgetEntityMapper.ToMapperRegistrator,
        WidgetProvider.ToProviderRegistrator,
        WidgetWorkerFactory.ToWorkerFactoryRegistrator,
        WidgetDbInitializer.ToDbInitializerRegistrator,
        WidgetRegistratorData

interface WidgetRegistratorData {
    fun putRegisterData(data: Set<WidgetRegistratorImpl.RegisterData>)
}

@ScopeApplication
class WidgetRegistratorImpl @Inject constructor() : WidgetRegistrator {

    interface RegisterData {
        fun id(): Int
        fun widgetDataCls(): KClass<out WidgetData>
        fun widgetConfigCls(): KClass<out WidgetConfig>
        fun widgetContentProvider(): Provider<out WidgetContent>
        fun widgetRefresher(): Provider<out WidgetRefresher>
        //fun widgetInit(): Provider<out WidgetInit>
    }

    private val registerData: MutableSet<RegisterData> = mutableSetOf()

    override fun putRegisterData(data: Set<RegisterData>) {
        registerData.clear()
        registerData.addAll(data)
    }

    override fun registerWidgetToMapper(registerFunc: (id: Int, widgetDataCls: KClass<out WidgetData>, widgetConfigCls: KClass<out WidgetConfig>) -> Unit) {
        registerData.forEach { registerFunc(it.id(), it.widgetDataCls(), it.widgetConfigCls()) }
    }

    override fun registerWidgetToProvider(registerFunc: (id: Int, widgetContentProvider: Provider<out WidgetContent>) -> Unit) {
        registerData.forEach { registerFunc(it.id(), it.widgetContentProvider()) }
    }

    override fun registerWidgetToWorkerFactory(registerFunc: (id: Int, widgetRefresher: Provider<out WidgetRefresher>) -> Unit) {
        registerData.forEach { registerFunc(it.id(), it.widgetRefresher()) }
    }

    override fun registerWidgetToDbInitializer(registerFunc: (id: Int) -> Unit) {
        registerData.forEach { registerFunc(it.id()) }
    }


}