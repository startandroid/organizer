package ru.startandroid.widgets.weatherwidget

//import ru.startandroid.widgets.db.WidgetInit
import ru.startandroid.organizer.home.widget.WeatherWidgetContent
import ru.startandroid.widgets.WIDGETS_IDS.WEATHER_WIDGET
import ru.startandroid.widgetsbase.WidgetConfig
import ru.startandroid.widgetsbase.WidgetData
import ru.startandroid.widgetsbase.adapter.content.WidgetContent
import ru.startandroid.widgetsbase.refresh.WidgetDbDataHelper
import ru.startandroid.widgetsbase.registrator.WidgetMetadatRepositoryImpl
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

data class WeatherWidgetData(
        val time: String,
        val tempMain: String,
        val temp1: String,
        val temp2: String,
        val temp3: String,
        val day1: String,
        val day2: String,
        val day3: String
) : WidgetData

data class WeatherWidgetConfig(
        val cities: List<City> = emptyList()
) : WidgetConfig

data class City(val id: Int, val name: String)


class WeatherWidgetWidgetMetadata @Inject constructor(
        val widgetContentProvider: Provider<WeatherWidgetContent>,
        val widgetRefresherProvider: Provider<WeatherWidgetDbDataHelper>

) : WidgetMetadatRepositoryImpl.WidgetMetadata {
    override fun id(): Int = WEATHER_WIDGET
    override fun widgetDataCls(): KClass<out WidgetData> = WeatherWidgetData::class
    override fun widgetConfigCls(): KClass<out WidgetConfig> = WeatherWidgetConfig::class
    override fun widgetContentProvider(): Provider<out WidgetContent> = widgetContentProvider
    override fun widgetRefresher(): Provider<out WidgetDbDataHelper> = widgetRefresherProvider
}