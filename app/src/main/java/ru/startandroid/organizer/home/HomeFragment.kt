package ru.startandroid.organizer.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.AndroidInjection
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
//import ru.startandroid.data.AppDatabase
import ru.startandroid.data.WeatherAPI
import ru.startandroid.organizer.R
import ru.startandroid.widgets.WidgetEntityMapper
import ru.startandroid.widgets.adapter.WidgetAdapter
import ru.startandroid.widgets.adapter.WidgetAdapterCallback
import ru.startandroid.widgets.refresh.WidgetsRefresher
import javax.inject.Inject

class HomeFragment : android.app.Fragment() {

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }

    @Inject
    lateinit var widgetAdapter: WidgetAdapter
    @Inject
    lateinit var weatherAPI: WeatherAPI

    override fun onAttach(context: Context?) {
        AndroidInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        init(view)

        return view
    }

    private fun init(view: View) {


        val linearLayoutManager = LinearLayoutManager(activity)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = widgetAdapter

        widgetAdapter.loadData()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        widgetAdapter.destroy()
    }
}
