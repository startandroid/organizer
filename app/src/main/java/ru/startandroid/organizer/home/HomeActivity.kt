package ru.startandroid.organizer.home

import android.app.Fragment // TODO fix that
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import ru.startandroid.organizer.R
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), HasFragmentInjector {
    override fun fragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction().replace(R.id.fragment_cont, HomeFragment.newInstance()).commit()
        }

    }
}
    