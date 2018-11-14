package ru.startandroid.organizer.home.widget.widgets

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.startandroid.organizer.R

class WidgetHolder(val view: View, val widgetContent: WidgetContent?) : RecyclerView.ViewHolder(view), WidgetContainerCallback {

    var widgetHeader = WidgetHeader()

    // TODO use data binding

    // TODO set TextView right side limit and ... if cropped
    private val headerTitle = view.findViewById<TextView>(R.id.header_title)
    private val refreshButton = view.findViewById<View>(R.id.refresh)
    private val settingsButton = view.findViewById<View>(R.id.settings)
    private val closeButton = view.findViewById<View>(R.id.close)

    private val content = view.findViewById<ViewGroup>(R.id.content)

    init {
        widgetContent?.run {
            setWidgetContainerCallback(this@WidgetHolder)
            content.addView(getView(content))
        }

        closeButton.setOnClickListener { onCloseButtonClick() }
        refreshButton.setOnClickListener { onRefreshButtonClick() }
        settingsButton.setOnClickListener { onSettingsButtonClick() }
    }

    private fun onCloseButtonClick() {
        // TODO show close dialog. If yes, disable this widget
    }

    private fun onSettingsButtonClick() {
        // TODO open settings screen
    }

    private fun onRefreshButtonClick() {
        widgetContent?.onRefreshClick()
    }

    fun bind(widgetDataEntity: WidgetDataEntity<out WidgetData>) {
        widgetContent?.setData(widgetDataEntity)
    }


    override fun setHeader(widgetHeader: WidgetHeader) {
        this.widgetHeader = widgetHeader
        updateHeader()
    }

    override fun getHeader(): WidgetHeader = widgetHeader

    private fun updateHeader() {
        headerTitle.text = widgetHeader.title
        refreshButton.visibility = if (widgetHeader.refreshButtonIsVisible) View.VISIBLE else View.GONE
        settingsButton.visibility = if (widgetHeader.settingsButtonIsVisible) View.VISIBLE else View.GONE
        closeButton.visibility = if (widgetHeader.closeButtonIsVisible) View.VISIBLE else View.GONE
    }

}