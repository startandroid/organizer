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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.startandroid.data.AppDatabase
import ru.startandroid.data.WeatherAPI
import ru.startandroid.organizer.R
import ru.startandroid.organizer.home.widget.common.WidgetDeepLinkBuilder
import ru.startandroid.organizer.home.widget.common.adapter.WidgetAdapter
import ru.startandroid.organizer.home.widget.common.WidgetEntityMapper
import ru.startandroid.organizer.home.widget.common.adapter.WidgetAdapterCallback
import javax.inject.Inject
import javax.inject.Provider

class HomeFragment : android.app.Fragment() {

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }

    @Inject
    lateinit var database: AppDatabase
    @Inject
    lateinit var widgetEntityMapper: WidgetEntityMapper
    @Inject
    lateinit var widgetAdapter: WidgetAdapter
    @Inject
    lateinit var weatherAPI: WeatherAPI

    @Inject
    lateinit var widgetDeepLinkBuilder: Provider<WidgetDeepLinkBuilder>
    @Inject
    lateinit var uiNavigator: UINavigator

    val compositeDisposable = CompositeDisposable()

    override fun onAttach(context: Context?) {
        AndroidInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        init(view)
        getWeather()
        return view
    }

    private fun init(view: View) {

        // TODO move to presenter
        val disposable = database.widgetDao()
                .getAll()
                .map {
                    it.map {
                        widgetEntityMapper.map(it)
                    }.filterNotNull()
                }.subscribe {
                    widgetAdapter.setWidgets(it)
                }
        compositeDisposable.add(disposable)

        // TODO move to presenter
        widgetAdapter.widgetAdapterCallback = object: WidgetAdapterCallback {
            override fun onWidgetRefreshClick(id: Int) {
                openWidgetDeepLink(id, WidgetDeepLinkBuilder.Action.REFRESH)
            }

            override fun onWidgetSettingsClick(id: Int) {
                openWidgetDeepLink(id, WidgetDeepLinkBuilder.Action.SETTINGS)
            }

            override fun onWidgetCloseClick(id: Int) {
                openWidgetDeepLink(id, WidgetDeepLinkBuilder.Action.CLOSE)
            }

        }

        val linearLayoutManager = LinearLayoutManager(activity)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = widgetAdapter

    }

    // TODO move to presenter
    private fun openWidgetDeepLink(id: Int, action: WidgetDeepLinkBuilder.Action) {
        val deepLink = widgetDeepLinkBuilder.get().widget(id).action(action).build()
        uiNavigator.openDeepLink(deepLink)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    private fun getWeather() {
        weatherAPI.getCityWeather("Paris").observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    Log.d("Result", "There are ${result.forecast.toString()} Java developers in Lagos")
                }, { error ->
                    error.printStackTrace()
                    Log.d("Result", "There are ${error.message.toString()} Java developers in Lagos")
                })
    }

}
