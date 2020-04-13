package ru.startandroid.widgets.weatherwidget.update


import ru.startandroid.widgets.weatherwidget.content.WeatherWidgetData
import ru.startandroid.widgetsbase.data.db.correct.WidgetCorrect
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.domain.model.WidgetData
import javax.inject.Inject

class WeatherWidgetCorrect @Inject constructor() : WidgetCorrect {
    override fun correctDataAccordingToConfig(data: WidgetData, config: WidgetConfig): WidgetData {
        return WeatherWidgetData("11:23", "25", "21", "19", "17", "day1", "day2", "day3")
    }
}