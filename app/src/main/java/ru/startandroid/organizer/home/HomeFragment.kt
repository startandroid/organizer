package ru.startandroid.organizer.home

import android.content.Context
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.AndroidInjection
import ru.startandroid.data.AppDatabase
import ru.startandroid.organizer.R
import ru.startandroid.organizer.adapters.HomeFragmentRvAdapter
import ru.startandroid.organizer.objects.SimpleObject
import java.util.*
import javax.inject.Inject


class HomeFragment : android.app.Fragment() {

    @Inject lateinit var database: AppDatabase

    override fun onAttach(context: Context?) {
        AndroidInjection.inject(this)
        super.onAttach(context)
    }


    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: HomeFragmentRvAdapter
    private lateinit var rootView: View
    lateinit var recyclerView: RecyclerView
    val data = ArrayList<SimpleObject>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_home, container, false)

        initVars()
        addFakeData()
        showData()

        return rootView
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }

    fun addFakeData() {
        data.add(SimpleObject("Object 1"))
        data.add(SimpleObject("Object 2"))
        data.add(SimpleObject("Object 3"))
        data.add(SimpleObject("Object 4"))
        data.add(SimpleObject("Object 5"))
    }

    fun initVars(){
        recyclerView = rootView.findViewById(R.id.recyclerView)
        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager
        adapter = HomeFragmentRvAdapter(data)
    }

    fun showData(){
        recyclerView.adapter = adapter
    }
}