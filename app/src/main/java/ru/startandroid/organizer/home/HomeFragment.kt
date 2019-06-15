package ru.startandroid.organizer.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.fragment_home.*
import ru.startandroid.organizer.R
import ru.startandroid.widgets.adapter.WidgetAdapter
import javax.inject.Inject

class HomeFragment : android.app.Fragment() {

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }

    @Inject
    lateinit var widgetAdapter: WidgetAdapter

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

        val floatingActionButton = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        floatingActionButton.setOnClickListener { var myAction = Uri.parse("app://organizer/widgets/config")
            var  intent = Intent(Intent.ACTION_VIEW, myAction)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)  }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        widgetAdapter.destroy()
    }
}
