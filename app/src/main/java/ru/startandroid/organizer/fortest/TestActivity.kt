package ru.startandroid.organizer.fortest

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.Observer
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Flowable
import kotlinx.android.synthetic.main.activity_test.*
import ru.startandroid.organizer.R

class TestActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.cont, TestFragment())
                    .commit()
        }

        val obs = Flowable.fromCallable {
            Log.d("qweee", "from callable")
            "1"
        }

        val ld = LiveDataReactiveStreams.fromPublisher(obs)

        ld.observeForever(Observer {
            Log.d("qweee", "observe $it")
        })


        textView.postDelayed({
            ld.observeForever(Observer {
                Log.d("qweee", "observe $it")
            })
        }, 1000)

    }

}
