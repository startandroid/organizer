package ru.startandroid.widgetsbase.ui.config

import android.content.Intent
import android.os.Bundle
import android.util.Log
import dagger.android.support.DaggerAppCompatActivity
import ru.startandroid.widgetsbase.R
import ru.startandroid.widgetsbase.ui.config.list.WidgetsConfigFragment
import ru.startandroid.widgetsbase.ui.config.widget.WidgetConfigContainerFragment

class WidgetsConfigActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_widgets_config)
        Log.d("qweee", "link = ${intent.data}")
        handleIntent(intent)
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
                .replace(R.id.container, WidgetsConfigFragment())
                .commit()
    }

    private fun showWidgetConfig(widgetId: Int) {
        Log.d("qweee", "show widget config $widgetId")
        val transaction = supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, WidgetConfigContainerFragment.newInstance(widgetId))

        if (supportFragmentManager.findFragmentById(R.id.container) != null) transaction.addToBackStack("widget config")

        transaction.commit()
    }
}
