package ru.startandroid.widgetsbase.ui.widgets.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.startandroid.widgetsbase.R
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import ru.startandroid.widgetsbase.ui.widgets.adapter.container.WidgetContainerHolder
import ru.startandroid.widgetsbase.ui.widgets.adapter.content.WidgetProvider
import javax.inject.Inject

class WidgetAdapter
@Inject
constructor(
        private val widgetProvider: WidgetProvider,
        private var widgetAdapterCallback: WidgetAdapterCallback
) : RecyclerView.Adapter<WidgetContainerHolder>() {

    val widgets = mutableListOf<WidgetDataEntity>()

    fun setWidgets(widgets: List<WidgetDataEntity>) {
        this.widgets.clear()
        this.widgets.addAll(widgets)
        // TODO diffutils, task 43
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WidgetContainerHolder {
        val widgetContent = widgetProvider.getWidget(viewType)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.widget_container, parent, false)
        return WidgetContainerHolder(view, widgetContent, widgetAdapterCallback)
    }

    override fun onBindViewHolder(containerHolder: WidgetContainerHolder, position: Int) {
        containerHolder.bind(widgets[position])
    }

    override fun getItemCount(): Int = widgets.size
    override fun getItemViewType(position: Int): Int = widgets[position].id

}