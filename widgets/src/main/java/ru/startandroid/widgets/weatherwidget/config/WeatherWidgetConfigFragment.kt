package ru.startandroid.widgets.weatherwidget.config

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_weather_widget_config.*
import ru.startandroid.widgets.R
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.ui.config.widget.BaseWidgetConfigFragment


class WeatherWidgetConfigFragment : BaseWidgetConfigFragment<WeatherWidgetConfig>() {

    override fun getLayoutId(): Int = R.layout.fragment_weather_widget_config

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cities.text = "cities count = ${getOriginalConfig().cities.size}"
    }

    override fun fillNewConfig(oldConfig: WeatherWidgetConfig): WidgetConfig {
        return oldConfig
    }

    override fun checkIfNewConfigIsValid(): Boolean {
        return true
    }

}
