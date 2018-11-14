package ru.startandroid.organizer.home.widget.widgets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.startandroid.organizer.R
import javax.inject.Inject

class WidgetAdapter @Inject constructor(private val widgetProvider: WidgetProvider) : RecyclerView.Adapter<WidgetHolder>() {

    val widgets = mutableListOf<WidgetDataEntity<out WidgetData>>()

    fun setWidgets(widgets: List<WidgetDataEntity<out WidgetData>>) {
        this.widgets.clear()
        this.widgets.addAll(widgets)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WidgetHolder {
        val widgetContent = widgetProvider.getWidget(viewType)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.widget_container, parent, false)
        return WidgetHolder(view, widgetContent)
    }

    override fun onBindViewHolder(holder: WidgetHolder, position: Int) {
        holder.bind(widgets[position])
    }

    override fun getItemCount(): Int = widgets.size
    override fun getItemViewType(position: Int): Int = widgets[position].id

}