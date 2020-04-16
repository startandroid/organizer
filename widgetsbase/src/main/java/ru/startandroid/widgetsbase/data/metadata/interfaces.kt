package ru.startandroid.widgetsbase.data.metadata


/**
 * Used to provide metadata of widget
 */
interface WidgetMetadataProvider {
    /**
     * Return id of widget
     */
    fun getWidgetId(): Int

    /**
     * Return metadata of widget. Use DSL builder: metadata
     */
    fun provideMetadata(): WidgetMetadata
}

interface WidgetMetadataRepository: WidgetRegistratorMetadataRepository {
    fun getWidgetMetadata(id: Int): WidgetMetadata
    fun getWidgetIds(): IntArray
}

interface WidgetRegistratorMetadataRepository {
    fun registerMetadata(widgetMetadataFactorySet: Set<WidgetMetadataProvider>)
}