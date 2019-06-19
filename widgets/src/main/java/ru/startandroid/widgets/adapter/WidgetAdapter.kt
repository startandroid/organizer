package ru.startandroid.widgets.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import ru.startandroid.widgets.R
import ru.startandroid.widgets.WidgetDataEntity
import ru.startandroid.widgets.adapter.container.WidgetContainerHolder
import ru.startandroid.widgets.adapter.content.WidgetProvider
import ru.startandroid.widgets.db.WidgetDatabase
import ru.startandroid.widgets.mapper.WidgetEntityMapper
import javax.inject.Inject

class WidgetAdapter
@Inject
constructor(
        private val widgetProvider: WidgetProvider,
        private val widgetDatabase: WidgetDatabase,
        private var widgetEntityMapper: WidgetEntityMapper,
        private var widgetAdapterCallback: WidgetAdapterCallback,
        private var context: Context
) : RecyclerView.Adapter<WidgetContainerHolder>() {

    val widgets = mutableListOf<WidgetDataEntity>()

    var disposable: Disposable? = null

    fun loadData() {
        disposable?.dispose()
        disposable =
                widgetDatabase.widgetDataDao()
                        .getAll()
                        .map {
                            it.map {
                                widgetEntityMapper.map(it)
                            }.filterNotNull()

                        }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            setWidgets(it)
                            notifyDataSetChanged()
                        }
    }

    private fun setWidgets(widgets: List<WidgetDataEntity>) {
        this.widgets.clear()
        this.widgets.addAll(widgets)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WidgetContainerHolder {
        val widgetContent = widgetProvider.getWidget(viewType)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.widget_container, parent, false)
        return WidgetContainerHolder(view, widgetContent, widgetAdapterCallback)
    }

    override fun onBindViewHolder(containerHolder: WidgetContainerHolder, position: Int) {
        containerHolder.bind(widgets[position])
    }

    fun destroy() {
        disposable?.dispose()
    }

    override fun getItemCount(): Int = widgets.size
    override fun getItemViewType(position: Int): Int = widgets[position].id

}