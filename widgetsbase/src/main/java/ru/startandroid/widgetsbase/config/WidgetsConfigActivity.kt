package ru.startandroid.widgetsbase.config

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.startandroid.widgetsbase.R


class WidgetsConfigActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_widgets_config)
        if (intent.data != null) {
            val pathSegments = intent.data.pathSegments
            if (pathSegments.size > 2 && pathSegments[2] != null)
                Toast.makeText(this, "Widget " + pathSegments[2].toString() + " config", Toast.LENGTH_LONG).show()
            else
                Toast.makeText(this, "Widgets list", Toast.LENGTH_LONG).show()
            intent.data = null
        }
    }
}