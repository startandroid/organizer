package ru.startandroid.organizer.home.widget.widgets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


object WIDGETS_IDS {
    const val TEST_WIDGET_1 = 1
    const val TEST_WIDGET_2 = 2
}

interface WidgetData

interface WidgetSettings

// TODO create WidgetDataEntity<out WidgetData> and use it everywhere
data class WidgetDataEntity<D : WidgetData>(
        val id: Int,
        val data: D
)

interface WidgetContent {
    fun setData(widgetDataEntity: WidgetDataEntity<out WidgetData>)
    fun getView(parent: ViewGroup): View
    fun setWidgetContainerCallback(callback: WidgetContainerCallback)
    fun onRefreshClick()
}

abstract class BaseWidgetContent<WidgetDataType>: WidgetContent {
    var callback: WidgetContainerCallback? = null

    override fun setWidgetContainerCallback(callback: WidgetContainerCallback) {
        this.callback = callback
    }

    fun setHeader(title: String = "",
                  refreshButtonIsVisible: Boolean = false,
                  settingsButtonIsVisible: Boolean = false,
                  closeButtonIsVisible: Boolean = false) {
        callback?.setHeader(WidgetHeader(title, refreshButtonIsVisible, settingsButtonIsVisible, closeButtonIsVisible))
    }

    abstract fun getLayoutId(): Int

    abstract fun onViewInflated(widgetView: View)

    abstract fun onDataSet(widgetData: WidgetDataType)

    override fun setData(widgetDataEntity: WidgetDataEntity<out WidgetData>) {
        onDataSet(widgetDataEntity.data as WidgetDataType)
    }

    override fun getView(parent: ViewGroup): View {
        val view = LayoutInflater.from(parent.context).inflate(getLayoutId(), parent, false)
        onViewInflated(view)
        return view
    }

    override fun onRefreshClick() {

    }
}

interface WidgetContainerCallback {
    fun getHeader(): WidgetHeader
    fun setHeader(widgetHeader: WidgetHeader)
}

data class WidgetHeader(
        val title: String = "",
        val refreshButtonIsVisible: Boolean = false,
        val settingsButtonIsVisible: Boolean = false,
        val closeButtonIsVisible: Boolean = false
)



