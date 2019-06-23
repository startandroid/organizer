package ru.startandroid.widgetsbase.adapter.content

import javax.inject.Inject
import javax.inject.Provider

class WidgetProvider
@Inject
constructor(
        val widgetMetadataRepository: WidgetContentMetadataRepository
) {

    interface WidgetContentMetadataRepository {
        fun getWidgetContentProvider(id: Int): Provider<out WidgetContent>?
    }

    fun getWidget(id: Int) = widgetMetadataRepository.getWidgetContentProvider(id)?.get()

}