package ru.startandroid.organizer

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import dagger.android.AndroidInjection
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.startandroid.widgets.WidgetConfig
import ru.startandroid.widgets.db.WidgetDatabase
import ru.startandroid.widgets.mapper.WidgetEntityMapper
import ru.startandroid.widgets.registrator.WidgetMetadatRepositoryImpl
import javax.inject.Inject


private const val ARG_WIDGET_ID = "widget_id"
private const val EXTRA_WIDGET_CONFIG = "widget_config"

class WidgetConfigContainerFragment : Fragment() {

    // TODO create presenter
    // TODO check all !!, it[0], ...

    private var widgetId: Int? = null
    private var widgetConfig: WidgetConfig? = null

    @Inject
    lateinit var widgetDatabase: WidgetDatabase

    @Inject
    lateinit var widgetEntityMapper: WidgetEntityMapper

    @Inject
    lateinit var widgetMetadatRepositoryImpl: WidgetMetadatRepositoryImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            widgetId = it.getInt(ARG_WIDGET_ID)
        }

        if (savedInstanceState == null) {
            firstInit()
        } else {
            init(savedInstanceState)
        }
    }

    private fun firstInit() {
        readWidgetConfigFromDb().subscribe(
                {
                    widgetConfig = it
                    createWidgetConfigFragment()
                },
                {
                    Log.d("qweee", "readWidgetConfigFromDb error $it")
                }
        )
    }

    private fun readWidgetConfigFromDb(): Single<WidgetConfig> {
        return widgetDatabase.widgetConfigDao().getById(widgetId!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { Log.d("qweee", "readWidgetConfigFromDb data $it") }
                .map {
                    widgetEntityMapper.mapConfigDbToConfig(it[0])?.config!!
                }
    }

    private fun createWidgetConfigFragment() {
        widgetConfig?.let {
            val widgetConfigFragment = (widgetMetadatRepositoryImpl.getConfigFragment(widgetId!!) as BaseWidgetConfigFragment<*>)
                    .withConfig(it)
            childFragmentManager
                    .beginTransaction()
                    .add(R.id.container, widgetConfigFragment)
                    .commit()
        }
    }

    private fun init(savedInstanceState: Bundle) {
        widgetConfig = savedInstanceState.getParcelable(EXTRA_WIDGET_CONFIG)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(EXTRA_WIDGET_CONFIG, widgetConfig)
    }

    fun onBackPressed(): Boolean {

        val newConfig = getWidgetConfigFromWidgetConfigFragment()
        Log.d("qweee", "new config $newConfig, old config $widgetConfig, equals: ${newConfig == widgetConfig}")

        if (widgetConfig != newConfig) {
            Toast.makeText(activity, "config was changed, save it", Toast.LENGTH_SHORT).show()
            return true
        }
        return false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.d("qweee", "cont onCreateView ${hashCode()} $savedInstanceState")
        val view = inflater.inflate(R.layout.fragment_widget_config_container, container, false)
        view.findViewById<TextView>(R.id.widgetId).text = "Widget ID = $widgetId"
        view.findViewById<Button>(R.id.save).setOnClickListener {
            save()
        }
        return view
    }

    private fun getWidgetConfigFromWidgetConfigFragment(): WidgetConfig {
        return (childFragmentManager.findFragmentById(R.id.container) as BaseWidgetConfigFragment<*>).getNewConfig()
    }

    private fun save() {
        val newConfig = getWidgetConfigFromWidgetConfigFragment()
        // TODO move this logic to a repository
        widgetDatabase.widgetConfigDao().getById(widgetId!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    widgetDatabase.widgetConfigDao().update(
                            it[0].copy(config = widgetEntityMapper.configToJson(newConfig))
                    ).subscribeOn(Schedulers.io()).subscribe()
                }
                .subscribe({
                    widgetConfig = newConfig
                    Log.d("qweee", "save config done")
                }, {
                    Log.d("qweee", "save config error $it")
                }
                )

    }

    override fun onAttach(context: Context?) {
        AndroidInjection.inject(this)
        super.onAttach(context)
    }


    companion object {
        @JvmStatic
        fun newInstance(widgetId: Int) =
                WidgetConfigContainerFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_WIDGET_ID, widgetId)
                    }
                }
    }
}
