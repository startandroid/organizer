package ru.startandroid.organizer.fortest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.*
import ru.startandroid.organizer.R
import javax.inject.Inject

class TestActivity : AppCompatActivity(), HasAndroidInjector {

    override fun androidInjector(): AndroidInjector<Any> = fragmentInjector

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        if (savedInstanceState == null) {
            supportFragmentManager
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
