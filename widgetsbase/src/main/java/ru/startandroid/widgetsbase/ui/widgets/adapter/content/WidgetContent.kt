package ru.startandroid.widgetsbase.ui.widgets.adapter.content

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import ru.startandroid.widgetsbase.ui.widgets.adapter.container.WidgetContainerData
import ru.startandroid.widgetsbase.ui.widgets.adapter.container.WidgetContainerDataCallback


interface WidgetContent {
    fun setData(widgetDataEntity: WidgetDataEntity)
    fun getView(parent: ViewGroup): View
    fun setWidgetContainerCallback(dataCallback: WidgetContainerDataCallback)
}

/**
 * Base class to create widget item view in recyclerview.
 */
abstract class BaseWidgetContent<WidgetDataType> : WidgetContent, LayoutContainer {

    private var widgetContainerDataCallback: WidgetContainerDataCallback? = null
    private var tempView: View? = null


    /**
     * This layout id will be used to create a View
     */
    abstract fun getLayoutId(): Int

    /**
     * Update content of the view according to new widget data
     */
    abstract fun onDataSet(widgetData: WidgetDataType)

    override val containerView: View?
        get() = tempView

    override fun setWidgetContainerCallback(callback: WidgetContainerDataCallback) {
        this.widgetContainerDataCallback = callback
    }

    fun updateContainerData(func: (oldData: WidgetContainerData) ->  WidgetContainerData) =
        widgetContainerDataCallback?.updateWidgetContainerData(func)

    private fun onViewInflated(widgetView: View) {
        tempView = widgetView
    }

    override fun setData(widgetDataEntity: WidgetDataEntity) {
        onDataSet(widgetDataEntity.data as WidgetDataType)
    }

    override fun getView(parent: ViewGroup): View {
        val view = LayoutInflater.from(parent.context).inflate(getLayoutId(), parent, false)
        onViewInflated(view)
        return view
    }


}

