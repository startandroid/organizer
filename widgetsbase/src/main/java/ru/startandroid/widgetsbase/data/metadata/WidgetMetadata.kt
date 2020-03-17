package ru.startandroid.widgetsbase.data.metadata

import ru.startandroid.widgetsbase.data.db.refresh.WidgetDbDataHelper
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.domain.model.WidgetData
import ru.startandroid.widgetsbase.ui.config.widget.BaseWidgetConfigFragment
import ru.startandroid.widgetsbase.ui.widgets.adapter.content.WidgetContent
import kotlin.reflect.KClass

fun metadata(init: WidgetMetadataDslBuilder.() -> Unit): WidgetMetadata {
    val widgetMetadataBuilder = WidgetMetadataDslBuilder()
    widgetMetadataBuilder.init()
    return widgetMetadataBuilder.build()
}

class WidgetMetadataDslBuilder: Element() {

    private lateinit var details: WidgetMetadataDetails
    private lateinit var content: WidgetMetadataContent
    private lateinit var header: WidgetMetadataHeader
    private lateinit var config: WidgetMetadataConfig
    private lateinit var update: WidgetMetadataUpdate

    fun details(init: WidgetMetadataDetails.() -> Unit) {
        details = WidgetMetadataDetails()
        details.init()
    }

    fun content(init: WidgetMetadataContent.() -> Unit) {
        content = WidgetMetadataContent()
        content.init()
    }

    fun header(init: WidgetMetadataHeader.() -> Unit) {
        header = WidgetMetadataHeader()
        header.init()
    }

    fun config(init: WidgetMetadataConfig.() -> Unit) {
        config = WidgetMetadataConfig()
        config.init()
    }

    fun update(init: WidgetMetadataUpdate.() -> Unit) {
        update = WidgetMetadataUpdate()
        update.init()
    }

    fun build(): WidgetMetadata {
        return WidgetMetadata(details, content, header, config, update)
    }
}

data class WidgetMetadata(
        val details: WidgetMetadataDetails,
        val content: WidgetMetadataContent,
        val header: WidgetMetadataHeader,
        val config: WidgetMetadataConfig,
        val update: WidgetMetadataUpdate
)

class WidgetMetadataDetails: Element() {
    var titleResId: Int = 0
    var descriptionResId: Int = 0
}

class WidgetMetadataContent: Element() {
    lateinit var widgetDataCls: KClass<out WidgetData>
    lateinit var widgetContent: () -> WidgetContent
}

class WidgetMetadataHeader: Element() {
    var refreshButtonIsVisible: Boolean = false
    var configButtonIsVisible: Boolean = false
    var closeButtonIsVisible: Boolean = false
}

class WidgetMetadataConfig: Element() {
    lateinit var widgetConfigCls: KClass<out WidgetConfig>
    lateinit var widgetConfigFragment: () -> BaseWidgetConfigFragment<*>
}

class WidgetMetadataUpdate: Element() {
    var autoRefresh: Boolean = false
    var needsInternet: Boolean = false
    lateinit var widgetRefresher: () -> WidgetDbDataHelper
}

@DslMarker
annotation class DslElementMarker

@DslElementMarker
abstract class Element
