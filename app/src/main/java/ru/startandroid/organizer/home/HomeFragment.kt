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
import ru.startandroid.data.AppDatabase
import ru.startandroid.organizer.R
import ru.startandroid.organizer.home.widget.common.adapter.WidgetAdapter
import ru.startandroid.organizer.home.widget.common.WidgetEntityMapper
import ru.startandroid.organizer.home.widget.common.adapter.WidgetAdapterCallback
import ru.startandroid.organizer.home.widget.refresh.WidgetsRefresher
import javax.inject.Inject


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
    lateinit var widgetsRefresher: WidgetsRefresher


    val compositeDisposable = CompositeDisposable()

    override fun onAttach(context: Context?) {
        AndroidInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        init(view)

        Log.d("qweee", "onCreateView HomeFragment $database")

        return view
    }

    private fun init(view: View) {

        // TODO move to presenter
        val disposable = database.widgetDao()
                .getAll()
                .doOnNext { Log.d("qweee", "refresh widget list $it") }
                .map {
                    it.map {
                        widgetEntityMapper.map(it)
                    }.filterNotNull()

                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    widgetAdapter.setWidgets(it)
                    widgetAdapter.notifyDataSetChanged()
                    // TODO use diffutils
                }
        compositeDisposable.add(disposable)

        // TODO move to presenter
        widgetAdapter.widgetAdapterCallback = object: WidgetAdapterCallback {
            override fun onWidgetRefreshClick(id: Int) {
                widgetsRefresher.refresh(id)
            }

            override fun onWidgetSettingsClick(id: Int) {
            }

            override fun onWidgetCloseClick(id: Int) {
            }

        }

        val linearLayoutManager = LinearLayoutManager(activity)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = widgetAdapter

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}