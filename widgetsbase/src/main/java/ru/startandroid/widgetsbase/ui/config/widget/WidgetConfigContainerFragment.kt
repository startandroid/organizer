package ru.startandroid.widgetsbase.ui.config.widget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.startandroid.dialoghelper.DialogConfig
import com.startandroid.dialoghelper.DialogHandler
import com.startandroid.dialoghelper.DialogHelper
import com.startandroid.dialoghelper.HasDialogHandler
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_widget_config_container.*
import ru.startandroid.device.extension.getViewModel
import ru.startandroid.widgetsbase.R
import ru.startandroid.widgetsbase.data.metadata.WidgetConfigScreenMetadataRepository
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
    lateinit var widgetMetadataRepositoryImpl: WidgetConfigScreenMetadataRepository

    private var widgetId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            widgetId = it.getInt(ARG_WIDGET_ID)
        }

        if (savedInstanceState == null) {
            getModel().getWidgetConfigEntity().observe(this, Observer {
                createWidgetConfigFragment(it)
                getModel().getWidgetConfigEntity().removeObservers(this)
            })
        }

        getModel().closeScreen.observe(this, Observer {
            activity?.onBackPressed()
        })

        registerDialogs()
    }

    private fun createWidgetConfigFragment(widgetConfigEntity: WidgetConfigEntity?) {
        widgetConfigEntity?.let {
            val widgetConfigFragment = (widgetMetadataRepositoryImpl.getConfigFragment(widgetId) as BaseWidgetConfigFragment<*>)
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
                    if (checkIfNewConfigIsValid()) getModel().onSaveDialogPositive(getNewConfig())
                }
                .negative(R.string.no) { getModel().onSaveDialogNegative() }
                .neutral(R.string.cancel) {}

        dialogHelper.registerDialogConfig(CODE_DIALOG_SAVE_CONFIG, config)

        getModel().showDialog.observe(this, Observer {
            dialogHelper.showDialog(it, this)
        })

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentWidgetConfigContainerBinding>(inflater, R.layout.fragment_widget_config_container, container, false)
        binding.widgetConfig = getModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveButton.setOnClickListener {
            if (checkIfNewConfigIsValid()) getModel().onSaveButtonPressed(getNewConfig())
        }
    }


    override fun dialogHandler(): DialogHandler? = dialogHelper

    fun onBackPressed(): Boolean = getModel().onBackPressed(getNewConfig())

    private fun getChildWidgetConfigFragment() = (childFragmentManager.findFragmentById(R.id.container) as BaseWidgetConfigFragment<*>)

    private fun getModel() = getViewModel(WidgetConfigContainerViewModel::class.java, widgetConfigContainerViewModelFactory)

    private fun getNewConfig(): WidgetConfig = getChildWidgetConfigFragment().getNewConfig()

    private fun checkIfNewConfigIsValid(): Boolean = getChildWidgetConfigFragment().checkIfNewConfigIsValid()

}