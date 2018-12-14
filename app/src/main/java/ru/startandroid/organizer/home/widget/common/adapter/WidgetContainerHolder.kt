package ru.startandroid.organizer.home.widget.common.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.startandroid.organizer.R
import ru.startandroid.organizer.home.UINavigator
import ru.startandroid.organizer.home.widget.common.*

class WidgetContainerHolder(val view: View, val widgetContent: WidgetContent?, val uiNavigator: UINavigator) : RecyclerView.ViewHolder(view), WidgetContainerCallback {

    private var widgetContainerData = WidgetContainerData()

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

        closeButton.setOnClickListener { onCloseButtonClick() }
        refreshButton.setOnClickListener { onRefreshButtonClick() }
        settingsButton.setOnClickListener { onSettingsButtonClick() }
    }

    private fun onCloseButtonClick() {
        // TODO show close dialog. If yes, disable this widget
    }

    private fun onSettingsButtonClick() {
        uiNavigator.openDeepLink(widgetContainerData.settingsUri)
    }

    private fun onRefreshButtonClick() {
        widgetContent?.onRefreshClick()
    }

    fun bind(widgetDataEntity: WidgetDataEntity<out WidgetData>) {
        widgetContent?.setData(widgetDataEntity)
    }


    override fun setWidgetContainerData(widgetData: WidgetContainerData) {
        this.widgetContainerData = widgetData
        updateHeader()
    }

    override fun getWidgetContainerData(): WidgetContainerData = widgetContainerData

    private fun updateHeader() {
        headerTitle.text = widgetContainerData.title
        refreshButton.visibility = if (widgetContainerData.refreshButtonIsVisible) View.VISIBLE else View.GONE
        settingsButton.visibility = if (widgetContainerData.settingsButtonIsVisible) View.VISIBLE else View.GONE
        closeButton.visibility = if (widgetContainerData.closeButtonIsVisible) View.VISIBLE else View.GONE
    }

}