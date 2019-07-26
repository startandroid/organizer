package ru.startandroid.organizer.fortest

import android.app.Fragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import ru.startandroid.organizer.R
import ru.startandroid.widgetsbase.ui.config.WidgetConfigContainerFragment
import javax.inject.Inject

class TestActivity : AppCompatActivity(), HasFragmentInjector {

    override fun fragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .add(R.id.cont, TestFragment())
                    .commit()
        }

//        if (savedInstanceState == null) {
//            fragmentManager
//                    .beginTransaction()
//                    .add(R.id.cont, WidgetConfigContainerFragment.newInstance(2))
//                    .commit()
//        }
//
//        onBackPressedDispatcher.addCallback {
//            (fragmentManager.findFragmentById(R.id.cont) as? WidgetConfigContainerFragment)
//                    ?.let {
//                        it.onBackPressed()
//                    } ?: false
//        }

    }

}
