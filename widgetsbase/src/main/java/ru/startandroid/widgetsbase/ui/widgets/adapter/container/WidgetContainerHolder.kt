package ru.startandroid.widgetsbase.ui.widgets.adapter.container

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.widget_container.*
import ru.startandroid.widgetsbase.data.DB_MAPPING
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadataRepository
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import ru.startandroid.widgetsbase.ui.widgets.adapter.WidgetAdapterCallback
import ru.startandroid.widgetsbase.ui.widgets.adapter.content.WidgetContent

class WidgetContainerHolder(override val containerView: View,
                            private val widgetId: Int,
                            widgetMetadata: WidgetMetadataRepository,
                            private val widgetAdapterCallback: WidgetAdapterCallback?)
    : RecyclerView.ViewHolder(containerView),
        WidgetContainerDataCallback, LayoutContainer {


    private lateinit var widgetContainerData: WidgetContainerData
    private var widgetContent: WidgetContent? = null


    init {
        widgetMetadata.getWidgetMetadata(widgetId).let {

            val widgetContainerData = WidgetContainerData(
                    id = widgetId,
                    title = containerView.resources.getString(it.details.titleResId),
                    refreshButtonIsVisible = it.header.refreshButtonIsVisible,
                    configButtonIsVisible = it.header.configButtonIsVisible,
                    closeButtonIsVisible = it.header.closeButtonIsVisible
            )
            updateHeaderUI(widgetContainerData)

            widgetContent = it.content.widgetContent.invoke()
        }

        widgetContent?.run {
            setWidgetContainerCallback(this@WidgetContainerHolder)
            content.addView(getView(content))
        }

        closeButton.setOnClickListener { widgetAdapterCallback?.onWidgetCloseClick(widgetContainerData.id) }
        refreshButton.setOnClickListener { widgetAdapterCallback?.onWidgetRefreshClick(widgetContainerData.id) }
        configButton.setOnClickListener { widgetAdapterCallback?.onWidgetSettingsClick(widgetContainerData.id) }
    }

    fun setRefreshStatus(status: Int) {
        if (!this.widgetContainerData.refreshButtonIsVisible) return
        refreshButton.visibility = if (status == DB_MAPPING.REFRESH_STATUS.DONE) View.VISIBLE else View.INVISIBLE
        refreshProgressBar.visibility = if (status == DB_MAPPING.REFRESH_STATUS.REFRESHING) View.VISIBLE else View.INVISIBLE
    }

    fun bind(widgetDataEntity: WidgetDataEntity) {
        widgetContent?.setData(widgetDataEntity)
        setRefreshStatus(widgetDataEntity.refreshStatus)
    }

    private fun updateHeaderUI(widgetContainerData: WidgetContainerData) {
        this.widgetContainerData = widgetContainerData
        this.widgetContainerData.run {
            headerTitle.text = title
            refreshButton.visibility = if (refreshButtonIsVisible) View.VISIBLE else View.GONE
            configButton.visibility = if (configButtonIsVisible) View.VISIBLE else View.GONE
            closeButton.visibility = if (closeButtonIsVisible) View.VISIBLE else View.GONE
        }
    }

    override fun updateWidgetContainerData(func: (oldData: WidgetContainerData) -> WidgetContainerData) {
        updateHeaderUI(func.invoke(widgetContainerData))
    }

}