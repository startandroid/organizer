package ru.startandroid.organizer.home.widget.widgets

import javax.inject.Inject
import javax.inject.Provider

class WidgetProvider @Inject constructor(widgetProviders: MutableMap<Int, Provider<WidgetContent>>) {

    // TODO use KClass
    private val widgets = mutableMapOf<Int, Provider<out WidgetContent>>()

    init {
        widgetProviders.forEach {
            registerWidget(it.key, it.value)
        }
    }

    private fun registerWidget(id: Int, widgetContentProvider: Provider<out WidgetContent>) {
        widgets.put(id, widgetContentProvider)
    }

    fun getWidget(id: Int) = widgets.get(id)?.get()

}