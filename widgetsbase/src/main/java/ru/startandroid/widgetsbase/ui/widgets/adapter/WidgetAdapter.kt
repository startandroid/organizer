package ru.startandroid.widgetsbase.ui.widgets.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.startandroid.widgetsbase.R
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadataRepository
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import ru.startandroid.widgetsbase.ui.widgets.adapter.container.WidgetContainerHolder
import javax.inject.Inject

class WidgetAdapter @Inject constructor(
        private val widgetMetadataRepository: WidgetMetadataRepository,
        private val widgetAdapterCallback: WidgetAdapterCallback
) : ListAdapter<WidgetDataEntity, WidgetContainerHolder>(diffCallback) {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WidgetContainerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.widget_container, parent, false)
        return WidgetContainerHolder(view, viewType, widgetMetadataRepository, widgetAdapterCallback)
    }

    override fun onBindViewHolder(containerHolder: WidgetContainerHolder, position: Int) {
        containerHolder.bind(getItem(position))
    }

    override fun onBindViewHolder(containerHolder: WidgetContainerHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            containerHolder.setRefreshStatus(payloads[0] as Int)
        }
        containerHolder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = getItem(position).id

    override fun getItemId(position: Int): Long =
            getItem(position).id.toLong()


    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<WidgetDataEntity>() {
            override fun areItemsTheSame(oldItem: WidgetDataEntity, newItem: WidgetDataEntity): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: WidgetDataEntity, newItem: WidgetDataEntity): Boolean =
                    oldItem.data.equals(newItem.data) && oldItem.refreshStatus == newItem.refreshStatus

            override fun getChangePayload(oldItem: WidgetDataEntity, newItem: WidgetDataEntity): Any? =
                    if (newItem.refreshStatus != oldItem.refreshStatus) newItem.refreshStatus else null
        }
    }

}