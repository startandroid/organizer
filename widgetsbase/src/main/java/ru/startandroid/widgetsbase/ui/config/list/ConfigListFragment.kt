package ru.startandroid.widgetsbase.ui.config.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_widgets_config.*
import ru.startandroid.device.delegation.viewModel
import ru.startandroid.widgetsbase.R
import ru.startandroid.widgetsbase.ui.WidgetsViewModelFactory
import ru.startandroid.widgetsbase.ui.config.list.adapter.ConfigListAdapter
import javax.inject.Inject

/**
 * Displays list of widgets names with enable/disable toggles. Click to any widget goes to config screen of this widget
 */
class ConfigListFragment : DaggerFragment() {

    @Inject
    lateinit var widgetsViewModelFactory: WidgetsViewModelFactory

    @Inject
    lateinit var adapter: ConfigListAdapter

    private val model by viewModel(ConfigListViewModel::class.java) { widgetsViewModelFactory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_widgets_config, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configList.layoutManager = LinearLayoutManager(requireContext())
        configList.adapter = adapter

        model.getConfigs().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        adapter.getClicks().observe(viewLifecycleOwner, Observer<Int> {
            model.onItemClick(it)
        })
        adapter.getEnables().observe(viewLifecycleOwner, Observer<Pair<Int, Boolean>> {
            model.onItemEnabled(it.first, it.second)
        })
    }

}

