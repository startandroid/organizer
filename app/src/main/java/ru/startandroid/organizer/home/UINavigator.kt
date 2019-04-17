package ru.startandroid.organizer.home

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log

// TODO move to device module
class UINavigator(val activity: Activity) {

    fun openDeepLink(uri: Uri?) {
        uri?.let {
            Log.d("qweee", "open deeplink ${it.toString()}")
            Intent(Intent.ACTION_VIEW, it).let {
                if (it.resolveActivity(activity.packageManager) != null) {
                    activity.startActivity(it)
                }
            }
        }
    }

    fun openDeepLink(url: String?) {
        url?.let {
            val uri = Uri.parse(url)
            openDeepLink(uri)
        }

    }
}