package ru.startandroid.organizer.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import ru.startandroid.organizer.home.widget.common.WIDGETS_IDS.ACTION_REFRESH

// TODO move to device module, use to open settings?
class DeepLinkNavigator(val activity: Activity) {

    private val scheme: String = "app"
    private val authority: String = "organizer"

    // TODO remove?
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


    // TODO remove?
    fun openDeepLink(url: String?) {
        url?.let {
            val uri = Uri.parse(url)
            openDeepLink(uri)
        }

    }

    // TODO change to settings path
    fun refreshWidget(id: Int) {
        val uri = Uri.parse("$scheme://$authority/widget/$id")
        val action = ACTION_REFRESH
        Log.d("qweee", "send broadcast intent action = $action, uri = $uri")
        Intent(action, uri).sendBroadcast(activity)
    }


    fun Intent.sendBroadcast(context: Context) {
        context.sendBroadcast(this)
    }
}