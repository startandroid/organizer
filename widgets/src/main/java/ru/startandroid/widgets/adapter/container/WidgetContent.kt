package ru.startandroid.widgets.adapter.container

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.startandroid.widgets.WidgetData
import ru.startandroid.widgets.WidgetDataEntity


interface WidgetContent {
    fun setData(widgetDataEntity: WidgetDataEntity)
    fun getView(parent: ViewGroup): View
    fun setWidgetContainerCallback(dataCallback: WidgetContainerDataCallback)
}

abstract class BaseWidgetContent<WidgetDataType> : WidgetContent {
    private var widgetContainerDataCallback: WidgetContainerDataCallback? = null

    override fun setWidgetContainerCallback(callback: WidgetContainerDataCallback) {
        this.widgetContainerDataCallback = callback
    }

    fun setContainerData(id: Int = 0,
                         title: String = "",
                         refreshButtonIsVisible: Boolean = false,
                         settingsButtonIsVisible: Boolean = false,
                         closeButtonIsVisible: Boolean = false) {
        widgetContainerDataCallback?.setWidgetContainerData(WidgetContainerData(id, title, refreshButtonIsVisible, settingsButtonIsVisible, closeButtonIsVisible))
    }

    abstract fun getLayoutId(): Int

    abstract fun onViewInflated(widgetView: View)

    abstract fun onDataSet(widgetData: WidgetDataType)

    override fun setData(widgetDataEntity: WidgetDataEntity) {
        onDataSet(widgetDataEntity.data as WidgetDataType)
    }

    override fun getView(parent: ViewGroup): View {
        val view = LayoutInflater.from(parent.context).inflate(getLayoutId(), parent, false)
        onViewInflated(view)
        return view
    }


}

