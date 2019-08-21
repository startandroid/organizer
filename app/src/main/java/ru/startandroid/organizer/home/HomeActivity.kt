package ru.startandroid.organizer.home

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import ru.startandroid.organizer.R
import ru.startandroid.widgetsbase.ui.widgets.WidgetsFragment

class HomeActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_cont, WidgetsFragment.newInstance()).commit()
        }

    }
}
    