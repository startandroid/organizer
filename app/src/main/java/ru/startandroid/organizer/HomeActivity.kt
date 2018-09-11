package ru.startandroid.organizer

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction

import android.os.Bundle

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_cont, HomeFragment.newInstance()).commit()
        }
    }
}
    