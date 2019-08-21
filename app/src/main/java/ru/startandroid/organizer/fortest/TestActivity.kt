package ru.startandroid.organizer.fortest

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import ru.startandroid.organizer.R
import ru.startandroid.widgetsbase.ui.config.widget.WidgetConfigContainerFragment

class TestActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.cont, WidgetConfigContainerFragment.newInstance(2))
                    .commit()
        }

    }

}
