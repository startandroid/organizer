package ru.startandroid.widgetsbase.adapter


import android.content.Context
import android.content.Intent
import android.net.Uri
import ru.startandroid.widgetsbase.refresh.WidgetsRefresher
import javax.inject.Inject

class WidgetAdapterCallback @Inject constructor(val widgetsRefresher: WidgetsRefresher) {
    fun onWidgetRefreshClick(id: Int) {
        widgetsRefresher.refresh(id)
    }

    fun onWidgetSettingsClick(id: Int, context: Context) {
        var myAction = Uri.parse("app://organizer/widgets/config/$id")
        var  intent = Intent(Intent.ACTION_VIEW, myAction)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun onWidgetCloseClick(id: Int) {

    }
}