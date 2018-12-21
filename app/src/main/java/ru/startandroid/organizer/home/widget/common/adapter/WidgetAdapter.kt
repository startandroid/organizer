package ru.startandroid.organizer.home.widget.common.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.startandroid.organizer.R
import ru.startandroid.organizer.home.widget.common.WidgetData
import ru.startandroid.organizer.home.widget.common.WidgetDataEntity
import javax.inject.Inject

class WidgetAdapter @Inject constructor(private val widgetProvider: WidgetProvider)
    : RecyclerView.Adapter<WidgetContainerHolder>() {


    var widgetAdapterCallback: WidgetAdapterCallback? = null

    val widgets = mutableListOf<WidgetDataEntity<out WidgetData>>()

    fun setWidgets(widgets: List<WidgetDataEntity<out WidgetData>>) {
        this.widgets.clear()
        this.widgets.addAll(widgets)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WidgetContainerHolder {
        val widgetContent = widgetProvider.getWidget(viewType)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.widget_container, parent, false)
        return WidgetContainerHolder(view, widgetContent, widgetAdapterCallback)
    }

    override fun onBindViewHolder(containerHolder: WidgetContainerHolder, position: Int) {
        Log.d("qweee", "onBindViewHolder $position")
        containerHolder.bind(widgets[position])
    }

    override fun getItemCount(): Int = widgets.size
    override fun getItemViewType(position: Int): Int = widgets[position].id

}