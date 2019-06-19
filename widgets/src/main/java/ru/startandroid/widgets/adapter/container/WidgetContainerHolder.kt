package ru.startandroid.widgets.adapter.container

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.widget_container.*
import ru.startandroid.widgets.WidgetDataEntity
import ru.startandroid.widgets.adapter.WidgetAdapterCallback
import ru.startandroid.widgets.adapter.content.WidgetContent


class WidgetContainerHolder(override val containerView: View,
                            private val widgetContent: WidgetContent?,
                            private val widgetAdapterCallback: WidgetAdapterCallback?)
    : RecyclerView.ViewHolder(containerView),
        WidgetContainerDataCallback, LayoutContainer {


    private lateinit var widgetContainerData: WidgetContainerData


    init {
        widgetContent?.run {
            setWidgetContainerCallback(this@WidgetContainerHolder)
            content.addView(getView(content))
        }

        closeButton.setOnClickListener { widgetAdapterCallback?.onWidgetCloseClick(widgetContainerData.id) }
        refreshButton.setOnClickListener { widgetAdapterCallback?.onWidgetRefreshClick(widgetContainerData.id) }
        configButton.setOnClickListener { widgetAdapterCallback?.onWidgetSettingsClick(widgetContainerData.id, containerView.context) }
    }

    fun bind(widgetDataEntity: WidgetDataEntity) {
        widgetContent?.setData(widgetDataEntity)
    }


    override fun setWidgetContainerData(widgetContainerData: WidgetContainerData) {
        this.widgetContainerData = widgetContainerData
        updateHeaderUI()
    }

    override fun getWidgetContainerData(): WidgetContainerData = widgetContainerData

    private fun updateHeaderUI() {
        widgetContainerData.run {
            headerTitle.text = title
            refreshButton.visibility = if (refreshButtonIsVisible) View.VISIBLE else View.GONE
            configButton.visibility = if (configButtonIsVisible) View.VISIBLE else View.GONE
            closeButton.visibility = if (closeButtonIsVisible) View.VISIBLE else View.GONE
        }
    }

}