package ru.startandroid.widgets.weatherwidget

import `in`.madapps.placesautocomplete.PlaceAPI
import `in`.madapps.placesautocomplete.adapter.PlacesAutoCompleteAdapter
import `in`.madapps.placesautocomplete.model.Place
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import kotlinx.android.synthetic.main.fragment_city_search.*
import ru.startandroid.data.network.PlacesAPI

import ru.startandroid.widgets.R

class CitySearchFragment : Fragment() {

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
                    val place = parent.getItemAtPosition(position) as Place
                    citySearchTv.setText(place.description)
                }
    }

}
