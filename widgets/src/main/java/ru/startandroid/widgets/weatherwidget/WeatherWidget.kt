package ru.startandroid.widgets.weatherwidget

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.startandroid.organizer.home.widget.WeatherWidgetContent
import ru.startandroid.widgets.R
import ru.startandroid.widgets.WIDGETS_IDS.WEATHER_WIDGET
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.ui.widgets.adapter.content.WidgetContent
import ru.startandroid.widgetsbase.ui.config.widget.BaseWidgetConfigFragment
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadata
import ru.startandroid.widgetsbase.data.db.refresh.WidgetDbDataHelper
import ru.startandroid.widgetsbase.domain.model.WidgetData
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
) : WidgetData()

@Parcelize
data class WeatherWidgetConfig(
        val cities: List<City> = emptyList()
) : WidgetConfig

@Parcelize
data class City(val id: Int, val name: String) : Parcelable


class WeatherWidgetWidgetMetadata @Inject constructor(
        val widgetContentProvider: Provider<WeatherWidgetContent>,
        val widgetRefresherProvider: Provider<WeatherWidgetDbDataHelper>

) : WidgetMetadata {
    override fun id(): Int = WEATHER_WIDGET
    override fun titleResId(): Int = R.string.weather_widget_title
    override fun descriptionResId(): Int = R.string.weather_widget_description
    override fun widgetDataCls(): KClass<out WidgetData> = WeatherWidgetData::class
    override fun widgetConfigCls(): KClass<out WidgetConfig> = WeatherWidgetConfig::class
    override fun widgetContentProvider(): Provider<out WidgetContent> = widgetContentProvider
    override fun widgetRefresher(): Provider<out WidgetDbDataHelper> = widgetRefresherProvider
    override fun widgetConfigFragment(): BaseWidgetConfigFragment<*> = WeatherWidgetConfigFragment()
}