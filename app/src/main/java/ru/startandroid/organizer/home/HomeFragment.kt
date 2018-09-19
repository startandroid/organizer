package ru.startandroid.organizer.home

import android.content.Context
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.AndroidInjection
import ru.startandroid.data.AppDatabase
import ru.startandroid.organizer.R
import javax.inject.Inject


class HomeFragment : android.app.Fragment() {

    @Inject lateinit var database: AppDatabase

    override fun onAttach(context: Context?) {
        AndroidInjection.inject(this)
        super.onAttach(context)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_fraagment, container, false)
    }

    companion object {

        fun newInstance(): HomeFragment = HomeFragment()
    }

}
