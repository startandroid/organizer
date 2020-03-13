package ru.startandroid.widgetsbase.ui.config.widget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.startandroid.dialoghelper.DialogConfig
import com.startandroid.dialoghelper.DialogHandler
import com.startandroid.dialoghelper.DialogHelper
import com.startandroid.dialoghelper.HasDialogHandler
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_widget_config_container.*
import ru.startandroid.device.delegation.viewModel
import ru.startandroid.widgetsbase.R
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadataRepository
import ru.startandroid.widgetsbase.databinding.FragmentWidgetConfigContainerBinding
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import ru.startandroid.widgetsbase.ui.config.widget.WidgetConfigContainerViewModel.Companion.CODE_DIALOG_SAVE_CONFIG
import javax.inject.Inject


const val ARG_WIDGET_ID = "widget_id"

class WidgetConfigContainerFragment : DaggerFragment(), HasDialogHandler {

    companion object {
        @JvmStatic
        fun newInstance(widgetId: Int) =
                WidgetConfigContainerFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_WIDGET_ID, widgetId)
                    }
                }
    }

    @Inject
    lateinit var widgetConfigContainerViewModelFactory: WidgetConfigContainerViewModelFactory

    @Inject
    lateinit var dialogHelper: DialogHelper

    @Inject
    lateinit var widgetMetadataRepository: WidgetMetadataRepository

    private val model by viewModel(WidgetConfigContainerViewModel::class.java) { widgetConfigContainerViewModelFactory }

    private var widgetId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            widgetId = it.getInt(ARG_WIDGET_ID)
        }

        if (savedInstanceState == null) {
            model.getWidgetConfigEntity().observe(this, Observer {
                createWidgetConfigFragment(it)
                model.getWidgetConfigEntity().removeObservers(this)
            })
        }

        model.closeScreen.observe(this, Observer {
            activity?.onBackPressed()
        })

        registerDialogs()
    }

    private fun createWidgetConfigFragment(widgetConfigEntity: WidgetConfigEntity?) {
        widgetConfigEntity?.let {
            val widgetConfigFragment = (widgetMetadataRepository.getWidgetMetadata(widgetId)?.config?.widgetConfigFragment as BaseWidgetConfigFragment<*>)
                    .withConfig(it.config)
            childFragmentManager
                    .beginTransaction()
                    .add(R.id.container, widgetConfigFragment)
                    .commit()
        }
    }

    private fun registerDialogs() {
        val config = DialogConfig().message(R.string.want_to_save_changed_config)
                .positive(R.string.yes) {
                    if (checkIfNewConfigIsValid()) model.onSaveDialogPositive(getNewConfig())
                }
                .negative(R.string.no) { model.onSaveDialogNegative() }
                .neutral(R.string.cancel) {}

        dialogHelper.registerDialogConfig(CODE_DIALOG_SAVE_CONFIG, config)

        model.showDialog.observe(this, Observer {
            dialogHelper.showDialog(it, this)
        })

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentWidgetConfigContainerBinding>(inflater, R.layout.fragment_widget_config_container, container, false)
        binding.widgetConfig = model
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveButton.setOnClickListener {
            if (checkIfNewConfigIsValid()) model.onSaveButtonPressed(getNewConfig())
        }

        ArrayAdapter.createFromResource(
                activity,
                R.array.update_interval,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            updateIntervalSpinner.adapter = adapter
        }

    }


    override fun dialogHandler(): DialogHandler? = dialogHelper

    fun onBackPressed(): Boolean = model.onBackPressed(getNewConfig())

    private fun getChildWidgetConfigFragment() = (childFragmentManager.findFragmentById(R.id.container) as BaseWidgetConfigFragment<*>)


    private fun getNewConfig(): WidgetConfig = getChildWidgetConfigFragment().getNewConfig()

    private fun checkIfNewConfigIsValid(): Boolean = getChildWidgetConfigFragment().checkIfNewConfigIsValid()

}