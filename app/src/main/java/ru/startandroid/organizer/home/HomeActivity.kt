package ru.startandroid.organizer.home

import android.app.Fragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import ru.startandroid.organizer.R
import ru.startandroid.widgetsbase.ui.widgets.WidgetsFragment
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), HasFragmentInjector {
    override fun fragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction().replace(R.id.fragment_cont, WidgetsFragment.newInstance()).commit()
        }

    }
}
    