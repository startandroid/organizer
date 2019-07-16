package ru.startandroid.widgets.weatherwidget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_weather_widget_config.*
import ru.startandroid.widgets.R
import ru.startandroid.widgetsbase.WidgetConfig
import ru.startandroid.widgetsbase.config.BaseWidgetConfigFragment


class WeatherWidgetConfigFragment : BaseWidgetConfigFragment<WeatherWidgetConfig>() {


    override fun getLayoutId(): Int = R.layout.fragment_weather_widget_config

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cities.text = "cities count = ${getOriginalConfig().cities.size}"
    }

    override fun getNewConfig(): WidgetConfig {
        return getOriginalConfig()
    }

}
