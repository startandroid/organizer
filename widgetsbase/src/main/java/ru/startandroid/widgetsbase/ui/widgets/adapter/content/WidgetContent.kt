package ru.startandroid.widgetsbase.ui.widgets.adapter.content

import android.content.Context
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
    private var _containerView: View? = null
    lateinit var context: Context


    /**
     * This layout id will be used to create a View
     */
    abstract fun getLayoutId(): Int

    /**
     * Update content of the view according to new widget data
     */
    abstract fun onDataSet(widgetData: WidgetDataType)

    override val containerView: View?
        get() = _containerView

    override fun setWidgetContainerCallback(callback: WidgetContainerDataCallback) {
        this.widgetContainerDataCallback = callback
    }

    fun updateContainerData(func: (oldData: WidgetContainerData) -> WidgetContainerData) =
            widgetContainerDataCallback?.updateWidgetContainerData(func)

    open fun onViewInflated(widgetView: View) {

    }

    override fun setData(widgetDataEntity: WidgetDataEntity) {
        onDataSet(widgetDataEntity.data as WidgetDataType)
    }

    override fun getView(parent: ViewGroup): View {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(getLayoutId(), parent, false)
        _containerView = view
        onViewInflated(view)
        return view
    }


}

