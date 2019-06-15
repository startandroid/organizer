package ru.startandroid.widgets.adapter.container

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.widget_container.*
import ru.startandroid.widgets.WidgetDataEntity
import ru.startandroid.widgets.adapter.WidgetAdapterCallback


class WidgetContainerHolder(override val containerView: View,
                            private val widgetContent: WidgetContent?,
                            private val widgetAdapterCallback: WidgetAdapterCallback?,
                            private var context: Context)
    : RecyclerView.ViewHolder(containerView),
        WidgetContainerDataCallback, LayoutContainer {


    private lateinit var widgetContainerData: WidgetContainerData

    // TODO use data binding

    // TODO set TextView right side limit and ... if cropped
    
    init {
        widgetContent?.run {
            setWidgetContainerCallback(this@WidgetContainerHolder)
            content.addView(getView(content))
        }

        closeButton.setOnClickListener { widgetAdapterCallback?.onWidgetCloseClick(widgetContainerData.id) }
        refreshButton.setOnClickListener { widgetAdapterCallback?.onWidgetRefreshClick(widgetContainerData.id) }
        settingsButton.setOnClickListener { widgetAdapterCallback?.onWidgetSettingsClick(widgetContainerData.id, context) }
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
            settingsButton.visibility = if (settingsButtonIsVisible) View.VISIBLE else View.GONE
            closeButton.visibility = if (closeButtonIsVisible) View.VISIBLE else View.GONE
        }
    }

}