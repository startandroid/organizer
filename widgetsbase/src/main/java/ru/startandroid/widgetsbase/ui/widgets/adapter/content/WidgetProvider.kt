package ru.startandroid.widgetsbase.ui.widgets.adapter.content

import ru.startandroid.widgetsbase.data.metadata.WidgetContentMetadataRepository
import javax.inject.Inject

class WidgetProvider
@Inject
constructor(
        val widgetMetadataRepository: WidgetContentMetadataRepository
) {


    fun getWidget(id: Int) = widgetMetadataRepository.getWidgetContentProvider(id)?.get()

}