package ru.startandroid.widgets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class WidgetsConfigActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_widgets_config)
        val data = intent.data
        val pathSegments = data.pathSegments
        if (pathSegments.size>2 && pathSegments[2] != null)
            Toast.makeText(this, pathSegments[2].toString(), Toast.LENGTH_LONG).show()
    }
}
