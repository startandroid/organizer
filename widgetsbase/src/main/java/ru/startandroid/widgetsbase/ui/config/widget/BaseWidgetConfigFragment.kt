package ru.startandroid.widgetsbase.ui.config.widget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.startandroid.widgetsbase.domain.model.WidgetConfig


private const val ARG_CONFIG = "config"

/**
 * Base fragment for any widget config screen.
 *
 * Return your layout id in getLayoutId method.
 * Use fillNewConfig to update config data from screen
 * Use checkIfNewConfigIsValid to check if data on the screen is valid
 *
 * Use usual lifecycle methods (except onCreateView) to manage view components
 *
 */
abstract class BaseWidgetConfigFragment<T : WidgetConfig> : Fragment() {

    private var argConfig: WidgetConfig? = null

    /**
     * Fill and return actual config data based on old config data and view components on the screen
     *
     * Called when need to save actual config to database
     */
    abstract fun fillNewConfig(oldConfig: T): WidgetConfig

    /**
     *  Check if data on the screen is valid and can be used as config.
     *
     *  The method is called before config data is about to be saved.
     *
     *  return true if everything is ok, false if data should be fixed
     */
    abstract fun checkIfNewConfigIsValid(): Boolean

    /**
     * This layout id will be used in onCreateView to create the screen
     */
    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            argConfig = it.getParcelable(ARG_CONFIG)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    fun getOriginalConfig(): T {
        return argConfig as T
    }

    fun getNewConfig(): WidgetConfig = fillNewConfig(getOriginalConfig())

    fun withConfig(config: WidgetConfig): BaseWidgetConfigFragment<T> {
        if (arguments == null) {
            arguments = Bundle()
        }
        arguments?.putParcelable(ARG_CONFIG, config)
        return this
    }
}
