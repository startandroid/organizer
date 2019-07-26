package ru.startandroid.widgetsbase.ui.config

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.startandroid.widgetsbase.domain.model.WidgetConfig


private const val ARG_CONFIG = "config"

abstract class BaseWidgetConfigFragment<T : WidgetConfig> : Fragment() {

    private var argConfig: WidgetConfig? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            argConfig = it.getParcelable(ARG_CONFIG)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(getLayoutId(), container, false)
    }

    fun getOriginalConfig(): T {
        return argConfig as T
    }

    abstract fun getNewConfig(): WidgetConfig
    abstract fun checkIfNewConfigIsValid(): Boolean
    abstract fun getLayoutId(): Int

    fun withConfig(config: WidgetConfig): BaseWidgetConfigFragment<T> {
        if (arguments == null) {
            arguments = Bundle()
        }
        arguments.putParcelable(ARG_CONFIG, config)
        return this
    }
}
