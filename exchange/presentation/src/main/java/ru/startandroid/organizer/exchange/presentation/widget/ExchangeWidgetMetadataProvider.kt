package ru.startandroid.organizer.exchange.presentation.widget

import ru.startandroid.organizer.exchange.presentation.R
import ru.startandroid.organizer.exchange.presentation.widget.config.ExchangeWidgetConfig
import ru.startandroid.organizer.exchange.presentation.widget.config.ExchangeWidgetConfigFragment
import ru.startandroid.organizer.exchange.presentation.widget.content.ExchangeWidgetContent
import ru.startandroid.organizer.exchange.presentation.widget.content.ExchangeWidgetData
import ru.startandroid.organizer.exchange.presentation.widget.update.ExchangeWidgetCorrect
import ru.startandroid.organizer.exchange.presentation.widget.update.ExchangeWidgetRefresh
import ru.startandroid.widgetsbase.WIDGETS_IDS
import ru.startandroid.widgetsbase.data.db.model.UpdateInterval
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadata
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadataProvider
import ru.startandroid.widgetsbase.data.metadata.metadata
import ru.startandroid.widgetsbase.domain.model.WidgetMainConfig
import javax.inject.Inject
import javax.inject.Provider

class ExchangeWidgetMetadataProvider @Inject constructor(
        private val exchangeWidgetContentProvider: Provider<ExchangeWidgetContent>,
        private val exchangeWidgetCorrect: ExchangeWidgetCorrect,
        private val exchangeWidgetRefresh: ExchangeWidgetRefresh
) : WidgetMetadataProvider {
    override fun getWidgetId(): Int = WIDGETS_IDS.EXCHANGE_WIDGET

    override fun provideMetadata(): WidgetMetadata {
        return metadata {
            details {
                titleResId = R.string.widget_title
                descriptionResId = R.string.widget_description
            }

            content {
                widgetDataCls = ExchangeWidgetData::class
                initWidgetData = ExchangeWidgetData("", emptyList(), "")
                widgetContent = { exchangeWidgetContentProvider.get() }
            }

            header {
                refreshButtonIsVisible = true
                configButtonIsVisible = true
                closeButtonIsVisible = true
            }

            config {
                widgetConfigCls = ExchangeWidgetConfig::class
                initWidgetConfig = ExchangeWidgetConfig(emptyList(), false)
                initWidgetMainConfig = WidgetMainConfig(true, UpdateInterval.HOUR_3)
                widgetConfigFragment = { ExchangeWidgetConfigFragment() }
            }

            update {
                needsInternet = true
                widgetCorrect = exchangeWidgetCorrect
                widgetRefresh = exchangeWidgetRefresh
            }
        }
    }
}