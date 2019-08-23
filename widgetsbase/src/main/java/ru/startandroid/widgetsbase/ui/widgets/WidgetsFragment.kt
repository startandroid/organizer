package ru.startandroid.widgetsbase.ui.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_widgets.*
import ru.startandroid.device.extension.getViewModel
import ru.startandroid.widgetsbase.R
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import ru.startandroid.widgetsbase.ui.WidgetsViewModelFactory
import ru.startandroid.widgetsbase.ui.widgets.adapter.WidgetAdapter
import javax.inject.Inject

class WidgetsFragment : DaggerFragment() {

    companion object {
        fun newInstance(): WidgetsFragment = WidgetsFragment()
    }

    @Inject
    lateinit var widgetAdapter: WidgetAdapter

    @Inject
    lateinit var widgetsViewModelFactory: WidgetsViewModelFactory


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_widgets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = widgetAdapter

        getModel().widgets().observe(viewLifecycleOwner, Observer<List<WidgetDataEntity>> {
            widgetAdapter.submitList(it)
        })

        floatingActionButton.setOnClickListener {
            getModel().onConfigButtonClick()
        }

    }

    private fun getModel() = getViewModel(WidgetsViewModel::class.java, widgetsViewModelFactory)

}