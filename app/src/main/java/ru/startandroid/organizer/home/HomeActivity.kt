package ru.startandroid.organizer.home

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import dagger.android.AndroidInjection
import ru.startandroid.organizer.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_cont, HomeFragment.newInstance()).commit()
        }

    }
}
    