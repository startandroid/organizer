package ru.startandroid.widgetsbase.data.metadata


interface WidgetMetadataProvider {
    fun getWidgetId(): Int
    fun provideMetadata(): WidgetMetadata
}

interface WidgetMetadataRepository: WidgetRegistratorMetadataRepository {
    fun getWidgetMetadata(id: Int): WidgetMetadata?
    fun getWidgetIds(): IntArray
}

interface WidgetRegistratorMetadataRepository {
    fun registerMetadata(widgetMetadataFactorySet: Set<WidgetMetadataProvider>)
}