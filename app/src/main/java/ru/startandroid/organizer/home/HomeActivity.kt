package ru.startandroid.organizer.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.*

import ru.startandroid.organizer.R
import ru.startandroid.widgetsbase.ui.widgets.WidgetsFragment
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), HasAndroidInjector {

    override fun androidInjector(): AndroidInjector<Any> = fragmentInjector

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_cont, WidgetsFragment.newInstance()).commit()
        }

    }
}
    