package ru.startandroid.widgets.weatherwidget

import ru.startandroid.organizer.home.widget.WeatherWidgetContent
import ru.startandroid.widgets.R
import ru.startandroid.widgets.WIDGETS_IDS.WEATHER_WIDGET
import ru.startandroid.widgets.weatherwidget.config.City
import ru.startandroid.widgets.weatherwidget.config.WeatherWidgetConfig
import ru.startandroid.widgets.weatherwidget.config.WeatherWidgetConfigFragment
import ru.startandroid.widgets.weatherwidget.content.WeatherWidgetData
import ru.startandroid.widgets.weatherwidget.update.WeatherWidgetCorrect
import ru.startandroid.widgets.weatherwidget.update.WeatherWidgetRefresh
import ru.startandroid.widgetsbase.data.db.model.UpdateInterval
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadata
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadataProvider
import ru.startandroid.widgetsbase.data.metadata.metadata
import ru.startandroid.widgetsbase.domain.model.WidgetMainConfig
import javax.inject.Inject

class WeatherWidget1MetadataProvider @Inject constructor() : WidgetMetadataProvider {

    override fun getWidgetId(): Int = WEATHER_WIDGET

    override fun provideMetadata(): WidgetMetadata {
        return metadata {

            details {
                titleResId = R.string.weather_widget_title
                descriptionResId = R.string.weather_widget_description
            }

            content {
                widgetDataCls = WeatherWidgetData::class
                widgetContent = WeatherWidgetContent()
                initWidgetData = WeatherWidgetData("11:23", "25", "21", "19", "17", "day1", "day2", "day3")
            }

            header {
                refreshButtonIsVisible = true
                configButtonIsVisible = true
                closeButtonIsVisible = true
            }

            config {
                widgetConfigCls = WeatherWidgetConfig::class
                widgetConfigFragment = { WeatherWidgetConfigFragment() }
                initWidgetConfig = WeatherWidgetConfig(listOf(City(1, "City 1"), City(2, "City 2")))
                initWidgetMainConfig = WidgetMainConfig(true, UpdateInterval.HOUR_3)
            }

            update {
                needsInternet = false
                widgetCorrect = WeatherWidgetCorrect()
                widgetRefresh = WeatherWidgetRefresh()
            }
        }
    }
}