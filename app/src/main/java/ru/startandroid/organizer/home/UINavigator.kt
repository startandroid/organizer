package ru.startandroid.organizer.home

import android.app.Activity
import android.content.Intent
import android.net.Uri

class UINavigator(val activity: Activity) {

    fun openDeepLink(uri: Uri?) {
        uri?.let {
            Intent(Intent.ACTION_VIEW, it).let {
                if (it.resolveActivity(activity.packageManager) != null) {
                    activity.startActivity(it)
                }
            }
        }

    }

}