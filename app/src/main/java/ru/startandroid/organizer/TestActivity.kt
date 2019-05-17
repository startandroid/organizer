package ru.startandroid.organizer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import dagger.android.AndroidInjection
import javax.inject.Inject

class TestActivity : AppCompatActivity() {

    //@Inject
    //lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        //Log.d("qweee", "onCreate testActivity $database")
    }
}
