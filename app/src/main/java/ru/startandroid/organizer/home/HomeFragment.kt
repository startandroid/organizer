package ru.startandroid.organizer.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.AndroidInjection
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import ru.startandroid.data.AppDatabase
import ru.startandroid.domain.WidgetEntity
import ru.startandroid.organizer.R
import ru.startandroid.organizer.adapters.HomeFragmentRvAdapter
import javax.inject.Inject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlin.collections.ArrayList


class HomeFragment : android.app.Fragment() {

    @Inject
    lateinit var database: AppDatabase

    override fun onAttach(context: Context?) {
        AndroidInjection.inject(this)
        super.onAttach(context)
    }

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: HomeFragmentRvAdapter
    private lateinit var rootView: View
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_home, container, false)

        initVars()
        subscribeForWidgets()
        refreshDataInDB() //delete all from DB and add new data

        return rootView
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }

    private fun initVars() {
        recyclerView = rootView.findViewById(R.id.recyclerView)
        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager
        adapter = HomeFragmentRvAdapter()
        recyclerView.adapter = adapter
    }

    private fun writeDataToDB(widgetsList: MutableList<WidgetEntity>) {
        Completable.fromAction {
            database.widgetDao().insert(widgetsList)

        }.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {}

                    override fun onComplete() {}

                    override fun onError(e: Throwable) {}
                })
    }

    private fun refreshDataInDB() {
        Completable.fromAction {
            database.widgetDao().deleteAll()

        }.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {}

                    override fun onComplete() {
                        writeDataToDB(createFakeData())
                    }

                    override fun onError(e: Throwable) {}
                })
    }

    private fun subscribeForWidgets() {
        database.widgetDao().getAll().observeOn(AndroidSchedulers.mainThread()).subscribe { widgetList ->
            adapter.setData(widgetList)
        }
    }

    private fun createFakeData(): ArrayList<WidgetEntity> {
        val fakeData = ArrayList<WidgetEntity>()
        fakeData.add(WidgetEntity(1, "Settings1", "Data1", 1, true))
        fakeData.add(WidgetEntity(2, "Settings2", "Data2", 1, true))
        fakeData.add(WidgetEntity(3, "Settings3", "Data", 1, true))
        fakeData.add(WidgetEntity(4, "Settings4", "Data", 1, true))
        fakeData.add(WidgetEntity(5, "Settings5", "Data", 1, true))
        return fakeData
    }

}