package ru.startandroid.widgetsbase.ui.config

import android.content.Intent
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import ru.startandroid.device.analytics.Analytics
import ru.startandroid.device.analytics.WidgetConfigEvent
import ru.startandroid.device.analytics.WidgetsConfigEvent
import ru.startandroid.widgetsbase.R
import ru.startandroid.widgetsbase.ui.config.list.ConfigListFragment
import ru.startandroid.widgetsbase.ui.config.widget.WidgetConfigContainerFragment
import javax.inject.Inject

/**
 * Displays:
 *
 * 1) list of widgets names
 *
 * 2) configuration screen for clicked widget
 */
class WidgetsConfigActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var analytics: Analytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_widgets_config)
        if (savedInstanceState == null) {
            handleIntent(intent)
        }
    }

    override fun onNewIntent(newIntent: Intent?) {
        super.onNewIntent(newIntent)
        handleIntent(newIntent)

    }

    private fun handleIntent(intent: Intent?) {
        intent?.data?.run {
            if (pathSegments.size > 2 && pathSegments[2] != null) {
                showWidgetConfig(pathSegments[2].toString().toInt())
            } else {
                showWidgetsConfig()
            }
        }
        intent?.data = null

    }


    private fun showWidgetsConfig() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, ConfigListFragment())
                .commit()
        analytics.logEvent(WidgetsConfigEvent())
    }

    private fun showWidgetConfig(widgetId: Int) {
        val transaction = supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, WidgetConfigContainerFragment.newInstance(widgetId))

        if (supportFragmentManager.findFragmentById(R.id.container) != null) transaction.addToBackStack("widget config")

        transaction.commit()
        analytics.logEvent(WidgetConfigEvent(widgetId))
    }

    override fun onBackPressed() {
        if ((supportFragmentManager.findFragmentById(R.id.container) as? WidgetConfigContainerFragment)
                        ?.onBackPressed() != true) {
            super.onBackPressed()
        }
    }

}
