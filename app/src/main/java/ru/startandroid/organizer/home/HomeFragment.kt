package ru.startandroid.organizer.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import ru.startandroid.data.AppDatabase
import ru.startandroid.organizer.R
import ru.startandroid.organizer.app.NetworkModule
import ru.startandroid.organizer.home.widget.common.adapter.WidgetAdapter
import ru.startandroid.organizer.home.widget.common.WidgetEntityMapper
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


    val compositeDisposable = CompositeDisposable()

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