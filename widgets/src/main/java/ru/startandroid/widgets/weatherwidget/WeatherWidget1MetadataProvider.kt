package ru.startandroid.widgets.weatherwidget

import ru.startandroid.organizer.home.widget.WeatherWidgetContent
import ru.startandroid.widgets.R
import ru.startandroid.widgets.WIDGETS_IDS.WEATHER_WIDGET
import ru.startandroid.widgets.weatherwidget.config.WeatherWidgetConfig
import ru.startandroid.widgets.weatherwidget.config.WeatherWidgetConfigFragment
import ru.startandroid.widgets.weatherwidget.content.WeatherWidgetData
import ru.startandroid.widgets.weatherwidget.update.WeatherWidgetDbDataHelper
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadata
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadataProvider
import ru.startandroid.widgetsbase.data.metadata.metadata
import javax.inject.Inject
import javax.inject.Provider


class WeatherWidget1MetadataProvider @Inject constructor(
        private val widgetContentProvider: Provider<WeatherWidgetContent>,
        private val widgetRefresherProvider: Provider<WeatherWidgetDbDataHelper>
) : WidgetMetadataProvider {

    override fun getWidgetId(): Int = WEATHER_WIDGET

    override fun provideMetadata(): WidgetMetadata {
        return metadata {

            details {
                titleResId = R.string.weather_widget_title
                descriptionResId = R.string.weather_widget_description
            }

            content {
                widgetDataCls = WeatherWidgetData::class
                widgetContent = widgetContentProvider
            }

            header {
                refreshButtonIsVisible = true
                configButtonIsVisible = true
                closeButtonIsVisible = true
            }

            config {
                widgetConfigCls = WeatherWidgetConfig::class
                widgetConfigFragment = WeatherWidgetConfigFragment()
            }

            update {
                autoRefresh = false
                needsInternet = false
                widgetRefresher = widgetRefresherProvider
            }
        }
    }
}