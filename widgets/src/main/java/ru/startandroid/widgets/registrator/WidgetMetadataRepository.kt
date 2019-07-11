package ru.startandroid.widgets.registrator

import android.app.Fragment
import ru.startandroid.domain.ScopeApplication
import ru.startandroid.widgets.WidgetConfig
import ru.startandroid.widgets.WidgetData
import ru.startandroid.widgets.adapter.content.WidgetContent
import ru.startandroid.widgets.adapter.content.WidgetProvider
import ru.startandroid.widgets.db.WidgetDbInitializer
import ru.startandroid.widgets.mapper.WidgetEntityMapper
import ru.startandroid.widgets.refresh.WidgetDbDataHelper
import ru.startandroid.widgets.refresh.WidgetWorkerFactory
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

interface WidgetMetadataRepository :
        WidgetEntityMapper.WidgetMappingMetadataRepository,
        WidgetProvider.WidgetContentMetadataRepository,
        WidgetWorkerFactory.WidgetDbDataHelperRepository,
        WidgetDbInitializer.WidgetDbInitMetadataRepository,
        WidgetRegistratorData

interface WidgetRegistratorData {
    fun putRegisterData(widgetMetadataSet: Set<WidgetMetadatRepositoryImpl.WidgetMetadata>)
}

@ScopeApplication
class WidgetMetadatRepositoryImpl @Inject constructor() : WidgetMetadataRepository {

    // TODO should be BaseWidgetConfigFragment
    fun getConfigFragment(id: Int): Fragment? {
        return widgetMetadata[id]?.widgetConfigFragment()
    }


    override fun getWidgetIds(): IntArray =
            widgetMetadata.keys.toIntArray()


    override fun getWidgetRefresherProvider(id: Int): Provider<out WidgetDbDataHelper>? =
            widgetMetadata[id]?.widgetRefresher()


    override fun getWidgetContentProvider(id: Int): Provider<out WidgetContent>? =
            widgetMetadata[id]?.widgetContentProvider()


    override fun getWidgetDataClass(id: Int): KClass<out WidgetData>? =
            widgetMetadata[id]?.widgetDataCls()


    override fun getWidgetConfigClass(id: Int): KClass<out WidgetConfig>? =
            widgetMetadata[id]?.widgetConfigCls()

    interface WidgetMetadata {
        fun id(): Int
        fun widgetDataCls(): KClass<out WidgetData>
        fun widgetConfigCls(): KClass<out WidgetConfig>
        fun widgetContentProvider(): Provider<out WidgetContent>
        fun widgetRefresher(): Provider<out WidgetDbDataHelper>
        fun widgetConfigFragment(): Fragment // TODO should be BaseWidgetConfigFragment
    }

    private val widgetMetadata: MutableMap<Int, WidgetMetadata> = mutableMapOf()

    override fun putRegisterData(widgetMetadataSet: Set<WidgetMetadata>) {
        for (data in widgetMetadataSet) {
            widgetMetadata[data.id()] = data
        }

    }

}