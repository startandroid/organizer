package ru.startandroid.organizer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_weather_widget_config.*
import ru.startandroid.organizer.home.widget.WeatherWidgetConfig
import ru.startandroid.widgets.WidgetConfig


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
