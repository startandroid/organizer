package ru.startandroid.device

import android.app.Activity
import android.content.Intent
import android.net.Uri
import ru.startandroid.domain.ScopeActivity
import javax.inject.Inject

@ScopeActivity
class Navigator @Inject constructor(
        val activity: Activity
) {

    fun openWidgetsConfig() {
        openDeepLink("app://organizer/widgets/config")
    }

    fun openWidgetConfig(id: Int) {
        openDeepLink("app://organizer/widgets/config/$id")
    }

    fun openTestActivity() {
        openDeepLink("app://organizer/test")
    }

    private fun openDeepLink(uri: String) {
        var myAction = Uri.parse(uri)
        var intent = Intent(Intent.ACTION_VIEW, myAction)
        activity.startActivity(intent)
    }
}