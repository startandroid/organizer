package ru.startandroid.device.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

open class Event(val name: String, val params: Bundle? = null)

class WidgetEnableEvent(widgetId: Int, enabled: Boolean) :
        Event("widget_enable", withWidgetId(widgetId) {
                    putBoolean("enabled", enabled)
                }
        )

class WidgetConfigEvent(widgetId: Int) :
        Event("widget_config", withWidgetId(widgetId))

class WidgetsConfigEvent() :
        Event("widgets_config")

class WidgetRefreshEvent(widgetId: Int) :
        Event("widget_refresh", withWidgetId(widgetId))


private fun withWidgetId(widgetId: Int, func: (Bundle.() -> Unit)? = null) =
        Bundle().apply {
            putInt(FirebaseAnalytics.Param.ITEM_ID, widgetId)
            func?.invoke(this)
        }
