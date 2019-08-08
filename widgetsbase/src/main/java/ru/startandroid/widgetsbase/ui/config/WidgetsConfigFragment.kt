package ru.startandroid.widgetsbase.ui.config

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.AndroidSupportInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_widgets_config.*
import ru.startandroid.device.Navigator
import ru.startandroid.widgetsbase.R
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository
import ru.startandroid.widgetsbase.ui.config.adapter.Config
import ru.startandroid.widgetsbase.ui.config.adapter.ConfigAdapter
import javax.inject.Inject

// TODO implement MVVM, task 66
class WidgetsConfigFragment : Fragment() {

    @Inject
    lateinit var navigator: Navigator

    private val adapter = ConfigAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("qweee", "WidgetsConfigFragment onCreate")
        adapter.getClicks().observe(this, Observer<Int> {
            onItemClick(it)
        })
        adapter.getEnables().observe(this, Observer<Pair<Int, Boolean>> {
            widgetConfigRepository.setEnabled(it.first, it.second)
                    .subscribeOn(Schedulers.io())
                    .subscribe({}, { Log.e("qweee", "setEnabled error $it") })
        })

        widgetConfigRepository.getAll()
                .map {
                    it.map {
                        Config(it.id, "title ${it.id}", it.enabled)
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            adapter.submitList(it)
                        },
                        {

                        }
                )
    }

    private fun onItemClick(id: Int) {
        navigator.openWidgetConfig(id)
    }

    @Inject
    lateinit var widgetConfigRepository: WidgetConfigRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_widgets_config, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("qweee", "WidgetsConfigFragment onViewCreated")

        val linearLayoutManager = LinearLayoutManager(requireContext())
        configList.layoutManager = linearLayoutManager
        configList.adapter = adapter


    }


    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

}
