package ru.startandroid.widgetsbase.data.metadata

import ru.startandroid.domain.ScopeApplication
import javax.inject.Inject

@ScopeApplication
class WidgetMetadataRepositoryImpl @Inject constructor() : WidgetMetadataRepository {

    private val widgetMetadata: MutableMap<Int, WidgetMetadata> = mutableMapOf()

    override fun registerMetadata(widgetMetadataFactorySet: Set<WidgetMetadataProvider>) {
        for (provider in widgetMetadataFactorySet) {
            widgetMetadata[provider.getWidgetId()] = provider.provideMetadata()
        }
    }

    override fun getWidgetMetadata(id: Int): WidgetMetadata? {
        return widgetMetadata[id]
    }

    override fun getWidgetIds() = widgetMetadata.keys.toIntArray()
}