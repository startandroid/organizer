package ru.startandroid.widgets.adapter.container

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.startandroid.widgets.R
import ru.startandroid.widgets.WidgetData
import ru.startandroid.widgets.WidgetDataEntity
import ru.startandroid.widgets.adapter.WidgetAdapterCallback


class WidgetContainerHolder(private val view: View,
                            private val widgetContent: WidgetContent?,
                            private val widgetAdapterCallback: WidgetAdapterCallback?)
    : RecyclerView.ViewHolder(view),
        WidgetContainerDataCallback {


    private lateinit var widgetContainerData: WidgetContainerData

    // TODO use data binding

    // TODO set TextView right side limit and ... if cropped
    private val headerTitle = view.findViewById<TextView>(R.id.header_title)
    private val refreshButton = view.findViewById<View>(R.id.refresh)
    private val settingsButton = view.findViewById<View>(R.id.settings)
    private val closeButton = view.findViewById<View>(R.id.close)

    private val content = view.findViewById<ViewGroup>(R.id.content)

    init {
        widgetContent?.run {
            setWidgetContainerCallback(this@WidgetContainerHolder)
            content.addView(getView(content))
        }

        closeButton.setOnClickListener { widgetAdapterCallback?.onWidgetCloseClick(widgetContainerData.id) }
        refreshButton.setOnClickListener { widgetAdapterCallback?.onWidgetRefreshClick(widgetContainerData.id) }
        settingsButton.setOnClickListener { widgetAdapterCallback?.onWidgetSettingsClick(widgetContainerData.id) }
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