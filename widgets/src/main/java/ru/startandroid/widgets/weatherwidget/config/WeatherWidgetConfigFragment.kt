package ru.startandroid.widgets.weatherwidget.config

import android.content.Intent
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_weather_widget_config.*
import ru.startandroid.widgets.R
import ru.startandroid.widgets.weatherwidget.CitySearchFragment
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.ui.config.widget.BaseWidgetConfigFragment


class WeatherWidgetConfigFragment : BaseWidgetConfigFragment<WeatherWidgetConfig>() {

    override fun getLayoutId(): Int = R.layout.fragment_weather_widget_config

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cities.text = "cities count = ${getOriginalConfig().cities.size}"
        chooseCityBtn.setOnClickListener {
            var citySearchFr = CitySearchFragment()
                //setTargetFragment(this@WeatherWidgetConfigFragment, REQ_CODE_CITY_FRAGMENT)
                requireActivity().supportFragmentManager
                        ?.beginTransaction()
                        ?.replace(ru.startandroid.widgetsbase.R.id.container, citySearchFr)
                        .addToBackStack(null)
                        ?.commit()

        }
    }

    override fun fillNewConfig(): WidgetConfig {
        return getOriginalConfig()
    }

    override fun checkIfNewConfigIsValid(): Boolean {
        return true
    }


}

const val REQ_CODE_CITY_FRAGMENT = 101