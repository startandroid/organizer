package ru.startandroid.organizer.home.widget.common.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.startandroid.organizer.home.widget.common.WidgetData
import ru.startandroid.organizer.home.widget.common.WidgetDataEntity


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

    fun setContainerData(title: String = "",
                         uri: Uri? = null,
                         refreshButtonIsVisible: Boolean = false,
                         settingsButtonIsVisible: Boolean = false,
                         closeButtonIsVisible: Boolean = false) {
        callback?.setWidgetContainerData(WidgetContainerData(title, uri, refreshButtonIsVisible, settingsButtonIsVisible, closeButtonIsVisible))
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

