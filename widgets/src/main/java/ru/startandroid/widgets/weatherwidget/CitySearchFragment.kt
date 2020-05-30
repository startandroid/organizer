package ru.startandroid.widgets.weatherwidget

import `in`.madapps.placesautocomplete.PlaceAPI
import `in`.madapps.placesautocomplete.adapter.PlacesAutoCompleteAdapter
import `in`.madapps.placesautocomplete.model.Place
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_city_search.*
import ru.startandroid.data.network.PlacesAPI

import ru.startandroid.widgets.R
import ru.startandroid.widgets.weatherwidget.config.WeatherWidgetConfigFragment
import ru.startandroid.widgets.weatherwidget.config.WeatherWidgetConfigFragment.Companion.REQ_CODE_CITY_FRAGMENT_RESULT

class CitySearchFragment : Fragment() {

    var place: Place? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_city_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val placesApi = PlaceAPI.Builder().apiKey("YOUR_API_KEY").build(requireContext())
        citySearchTv.setAdapter(PlacesAutoCompleteAdapter(requireContext(), placesApi))

        citySearchTv.onItemClickListener =
                AdapterView.OnItemClickListener { parent, _, position, _ ->
                    place = parent.getItemAtPosition(position) as Place
                    citySearchTv.setText(place?.description)
                }

        submitBtn.setOnClickListener {
            if (place!=null) {
                var intent =  Intent()
                intent.putExtra(REQ_CODE_CITY_FRAGMENT_RESULT, place?.description)
                var fragment = targetFragment
                fragment?.onActivityResult(WeatherWidgetConfigFragment.REQ_CODE_CITY_FRAGMENT, Activity.RESULT_OK, intent)
                requireActivity().supportFragmentManager.popBackStack()
            } else {
                Toast.makeText(requireContext(), getString(R.string.choose_city_first), Toast.LENGTH_SHORT).show()
            }
        }
    }

}
