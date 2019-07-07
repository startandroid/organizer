package ru.startandroid.organizer

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        Log.d("qweee", "act onCreate")
        setContentView(R.layout.activity_test)

        if (savedInstanceState == null) {
            Log.d("qweee", "act add frag")
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.cont, WidgetConfigContainerFragment.newInstance(3))
                    .commit()
        }

        onBackPressedDispatcher.addCallback {
            (supportFragmentManager.findFragmentById(R.id.cont) as? WidgetConfigContainerFragment)
                    ?.let {
                        it.onBackPressed()
                    } ?: false
        }

    }

}
