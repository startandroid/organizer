package ru.startandroid.widgetsbase.metadata

import ru.startandroid.domain.ScopeApplication
import javax.inject.Inject

@ScopeApplication
class WidgetMetadatRepositoryImpl @Inject constructor() : WidgetMetadataRepository {

    private val widgetMetadata: MutableMap<Int, WidgetMetadata> = mutableMapOf()

    override fun registerData(widgetMetadataSet: Set<WidgetMetadata>) {
        for (data in widgetMetadataSet) {
            widgetMetadata[data.id()] = data
        }
    }


    override fun getWidgetTitleResId(id: Int) = widgetMetadata[id]?.titleResId()

    override fun getWidgetDescriptionResId(id: Int) = widgetMetadata[id]?.descriptionResId()

    override fun getConfigFragment(id: Int) = widgetMetadata[id]?.widgetConfigFragment()

    override fun getWidgetIds() = widgetMetadata.keys.toIntArray()

    override fun getWidgetRefresherProvider(id: Int) = widgetMetadata[id]?.widgetRefresher()

    override fun getWidgetContentProvider(id: Int) = widgetMetadata[id]?.widgetContentProvider()

    override fun getWidgetDataClass(id: Int) = widgetMetadata[id]?.widgetDataCls()

    override fun getWidgetConfigClass(id: Int) = widgetMetadata[id]?.widgetConfigCls()


}