package ru.startandroid

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log

class DeepLinkNavigator(val activity: Activity) {

    private val scheme: String = "app"
    private val authority: String = "organizer"


    fun openDeepLink(uri: Uri?) {
        uri?.let {
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


//    fun refreshWidget(id: Int) {
//        val uri = Uri.parse("$scheme://$authority/widget/$id")
//        val action = ACTION_REFRESH
//        Log.d("qweee", "send broadcast intent action = $action, uri = $uri")
//        Intent(action, uri).sendBroadcast(activity)
//    }


    fun Intent.sendBroadcast(context: Context) {
        context.sendBroadcast(this)
    }
}