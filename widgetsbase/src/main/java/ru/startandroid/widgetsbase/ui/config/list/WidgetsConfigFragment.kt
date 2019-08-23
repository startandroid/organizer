package ru.startandroid.widgetsbase.ui.config.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_widgets_config.*
import ru.startandroid.device.Navigator
import ru.startandroid.device.extension.getViewModel
import ru.startandroid.widgetsbase.R
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository
import ru.startandroid.widgetsbase.ui.WidgetsViewModelFactory
import ru.startandroid.widgetsbase.ui.config.list.adapter.Config
import ru.startandroid.widgetsbase.ui.config.list.adapter.ConfigAdapter
import javax.inject.Inject

class WidgetsConfigFragment : DaggerFragment() {

    @Inject
    lateinit var widgetsViewModelFactory: WidgetsViewModelFactory

    @Inject
    lateinit var adapter: ConfigAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_widgets_config, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configList.layoutManager = LinearLayoutManager(requireContext())
        configList.adapter = adapter

        getModel().getConfigs().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        adapter.getClicks().observe(this, Observer<Int> {
            getModel().onItemClick(it)
        })
        adapter.getEnables().observe(this, Observer<Pair<Int, Boolean>> {
            getModel().onItemEnabled(it.first, it.second)
        })
    }

    private fun getModel() = getViewModel(WidgetsConfigViewModel::class.java, widgetsViewModelFactory)

}

