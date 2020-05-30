package ru.startandroid.widgets.weatherwidget.config

import android.app.Activity
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
            citySearchFr.setTargetFragment(this@WeatherWidgetConfigFragment, REQ_CODE_CITY_FRAGMENT)

               parentFragmentManager
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode === Activity.RESULT_OK) {
            if (requestCode === REQ_CODE_CITY_FRAGMENT) {
                 data?.getStringExtra(REQ_CODE_CITY_FRAGMENT_RESULT)
            }
        }
    }

companion object  {
    const val REQ_CODE_CITY_FRAGMENT = 101
    const val REQ_CODE_CITY_FRAGMENT_RESULT = "Result"


}
}



