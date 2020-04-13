package ru.startandroid.widgetsbase.data.metadata

import ru.startandroid.widgetsbase.data.db.correct.WidgetCorrect
import ru.startandroid.widgetsbase.data.db.refresh.WidgetRefresh
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.domain.model.WidgetData
import ru.startandroid.widgetsbase.domain.model.WidgetMainConfig
import ru.startandroid.widgetsbase.ui.config.widget.BaseWidgetConfigFragment
import ru.startandroid.widgetsbase.ui.widgets.adapter.content.WidgetContent
import kotlin.reflect.KClass

/**
 * DSL builder to create widget metadata
 */
fun metadata(init: WidgetMetadataDslBuilder.() -> Unit): WidgetMetadata {
    val widgetMetadataBuilder = WidgetMetadataDslBuilder()
    widgetMetadataBuilder.init()
    return widgetMetadataBuilder.build()
}

// TODOL move init classes from metadata? They are used only once!

class WidgetMetadataDslBuilder: Element() {

    private lateinit var details: WidgetMetadataDetails
    private lateinit var content: WidgetMetadataContent
    private lateinit var header: WidgetMetadataHeader
    private lateinit var config: WidgetMetadataConfig
    private lateinit var refresh: WidgetMetadataRefresh

    /**
     * Widget details:
     *
     * titleResId - string resource id for title
     *
     * descriptionResId - string resource id for description
     */
    fun details(init: WidgetMetadataDetails.() -> Unit) {
        details = WidgetMetadataDetails()
        details.init()
    }

    /**
     * Widget content:
     *
     * widgetDataCls - class for widget data
     *
     * widgetContent - object that will be used to create widget view in recyclerview
     *
     * initWidgetData - init data for widget
     */
    fun content(init: WidgetMetadataContent.() -> Unit) {
        content = WidgetMetadataContent()
        content.init()
    }

    /**
     * Widget header:
     *
     * refreshButtonIsVisible - visibility of refresh button
     *
     * configButtonIsVisible - visibility of config button
     *
     * closeButtonIsVisible - visibility of close button
     *
     */
    fun header(init: WidgetMetadataHeader.() -> Unit) {
        header = WidgetMetadataHeader()
        header.init()
    }

    /**
     * Widget configuration:
     *
     * widgetConfigCls - class for widget config
     *
     * widgetConfigFragment - fragment that shows configuration screen for widget
     *
     * initWidgetConfig - init config for widget
     *
     * initWidgetMainConfig - init main config for widget
     *
     */
    fun config(init: WidgetMetadataConfig.() -> Unit) {
        config = WidgetMetadataConfig()
        config.init()
    }

    /**
     * Widget update:
     *
     * autoRefresh - if widget data should be updated periodically
     *
     * needsInternet - if update task requires internet
     *
     * widgetCorrect - correct widget data according to config
     *
     * widgetRefresh - refresh widget data according to config
     *
     */
    fun update(init: WidgetMetadataRefresh.() -> Unit) {
        refresh = WidgetMetadataRefresh()
        refresh.init()
    }

    fun build(): WidgetMetadata {
        return WidgetMetadata(details, content, header, config, refresh)
    }
}

data class WidgetMetadata(
        val details: WidgetMetadataDetails,
        val content: WidgetMetadataContent,
        val header: WidgetMetadataHeader,
        val config: WidgetMetadataConfig,
        val refresh: WidgetMetadataRefresh
)

class WidgetMetadataDetails: Element() {
    var titleResId: Int = 0
    var descriptionResId: Int = 0
}

class WidgetMetadataContent: Element() {
    lateinit var widgetDataCls: KClass<out WidgetData>
    lateinit var initWidgetData: WidgetData
    lateinit var widgetContent: WidgetContent
}

class WidgetMetadataHeader: Element() {
    var refreshButtonIsVisible: Boolean = false
    var configButtonIsVisible: Boolean = false
    var closeButtonIsVisible: Boolean = false
}

class WidgetMetadataConfig: Element() {
    lateinit var widgetConfigCls: KClass<out WidgetConfig>
    lateinit var initWidgetConfig: WidgetConfig
    lateinit var initWidgetMainConfig: WidgetMainConfig
    lateinit var widgetConfigFragment: () -> BaseWidgetConfigFragment<*>
}

class WidgetMetadataRefresh: Element() {
    var needsInternet: Boolean = false
    var widgetCorrect: WidgetCorrect? = null
    var widgetRefresh: WidgetRefresh? = null
}

@DslMarker
annotation class DslElementMarker

@DslElementMarker
abstract class Element
