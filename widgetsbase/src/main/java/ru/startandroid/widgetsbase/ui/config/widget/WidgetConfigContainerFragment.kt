package ru.startandroid.widgetsbase.ui.config.widget

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.startandroid.dialoghelper.DialogConfig
import com.startandroid.dialoghelper.DialogHandler
import com.startandroid.dialoghelper.DialogHelper
import com.startandroid.dialoghelper.HasDialogHandler
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_widget_config_container.*
import ru.startandroid.widgetsbase.R
import ru.startandroid.widgetsbase.data.metadata.WidgetConfigScreenMetadataRepository
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository
import javax.inject.Inject


private const val ARG_WIDGET_ID = "widget_id"
private const val EXTRA_WIDGET_CONFIG = "widget_config"

class WidgetConfigContainerFragment : Fragment(), HasDialogHandler {

    companion object {
        private const val CODE_DIALOG_SAVE_CONFIG = 1

        @JvmStatic
        fun newInstance(widgetId: Int) =
                WidgetConfigContainerFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_WIDGET_ID, widgetId)
                    }
                }
    }


    override fun dialogHandler(): DialogHandler? = dialogHelper

    // TODO implement MVVM, task 66
    var widgetId: Int = 0
    private var widgetConfigEntity: WidgetConfigEntity? = null

    @Inject
    lateinit var widgetConfigRepository: WidgetConfigRepository

    @Inject
    lateinit var dialogHelper: DialogHelper

    @Inject
    lateinit var widgetMetadataRepositoryImpl: WidgetConfigScreenMetadataRepository

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

        registerDialogs()
    }

    private fun registerDialogs() {
        val config = DialogConfig().message(R.string.want_to_save_changed_config)
                .positive(R.string.yes) { save(); close() }
                .negative(R.string.no) { close() }
                .neutral(R.string.cancel) {}
        dialogHelper.registerDialogConfig(CODE_DIALOG_SAVE_CONFIG, config)
    }

    private fun firstInit() {
        val disposable = readWidgetConfigFromDb().subscribe(
                {
                    widgetConfigEntity = it
                    enabledToggle.setCheckedQuiet(it.enabled)
                    createWidgetConfigFragment()
                },
                {
                    Log.d("qweee", "readWidgetConfigFromDb error $it")
                }
        )
    }

    private fun readWidgetConfigFromDb(): Single<WidgetConfigEntity> {
        return widgetConfigRepository.getById(widgetId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { Log.d("qweee", "readWidgetConfigFromDb data $it") }
    }

    private fun createWidgetConfigFragment() {
        widgetConfigEntity?.let {
            val widgetConfigFragment = (widgetMetadataRepositoryImpl.getConfigFragment(widgetId) as BaseWidgetConfigFragment<*>)
                    .withConfig(it.config)
            childFragmentManager
                    .beginTransaction()
                    .add(R.id.container, widgetConfigFragment)
                    .commit()
        }
    }

    private fun init(savedInstanceState: Bundle) {
        widgetConfigEntity = savedInstanceState.getParcelable(EXTRA_WIDGET_CONFIG)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(EXTRA_WIDGET_CONFIG, widgetConfigEntity)
    }

    fun onBackPressed(): Boolean {
        if (configWasChanged()) {
            dialogHelper.showDialog(CODE_DIALOG_SAVE_CONFIG, this)
            return true
        }
        return false
    }

    fun close() {
        // TODO implement in task 66
        Log.d("qweee", "close screen")
    }

    fun configWasChanged(): Boolean {
        val newConfig = getNewConfig()
        Log.d("qweee", "new config $newConfig, old config ${widgetConfigEntity?.config}, equals: ${newConfig == widgetConfigEntity?.config}")

        return (widgetConfigEntity?.config != newConfig || widgetConfigEntity?.enabled != enabledToggle.isChecked)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.d("qweee", "cont onCreateView ${hashCode()} $savedInstanceState")
        return inflater.inflate(R.layout.fragment_widget_config_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveButton.setOnClickListener {
            save()
        }
        widgetMetadataRepositoryImpl.getWidgetTitleResId(widgetId)?.let { widgetTitle.setText(it) }
        widgetMetadataRepositoryImpl.getWidgetDescriptionResId(widgetId)?.let { widgetDescription.setText(it) }

        enabledToggle.setOnCheckedChangeListener { buttonView, isChecked ->
            Log.d("qweee", "config container click, $widgetId $isChecked")
            widgetConfigRepository.setEnabled(widgetId, isChecked)
                    .subscribeOn(Schedulers.io())
                    .subscribe({}, { Log.e("qweee", "setEnabled error $it") })
        }
    }

    private fun getNewConfig(): WidgetConfig = getChildWidgetConfigFragment().getNewConfig()

    private fun checkIfNewConfigIsValid(): Boolean = getChildWidgetConfigFragment().checkIfNewConfigIsValid()

    private fun getChildWidgetConfigFragment() = (childFragmentManager.findFragmentById(R.id.container) as BaseWidgetConfigFragment<*>)

    private fun save() {
        val newConfig = getNewConfig()

        val disposable = widgetConfigRepository.update(widgetId, newConfig, enabledToggle.isChecked)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("qweee", "save config done $it")
                    // remove it after implementing closing screen after saving
                    widgetConfigEntity = widgetConfigEntity?.copy(config = newConfig, enabled = enabledToggle.isChecked)

                }, { Log.d("qweee", "save config error $it") }
                )
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }


}