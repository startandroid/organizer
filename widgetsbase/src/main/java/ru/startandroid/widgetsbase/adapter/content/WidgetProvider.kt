package ru.startandroid.widgetsbase.adapter.content

import ru.startandroid.widgetsbase.metadata.WidgetContentMetadataRepository
import javax.inject.Inject

class WidgetProvider
@Inject
constructor(
        val widgetMetadataRepository: WidgetContentMetadataRepository
) {


    fun getWidget(id: Int) = widgetMetadataRepository.getWidgetContentProvider(id)?.get()

}