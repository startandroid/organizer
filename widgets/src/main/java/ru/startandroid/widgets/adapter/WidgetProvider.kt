package ru.startandroid.widgets.adapter

import ru.startandroid.widgets.adapter.container.WidgetContent
import javax.inject.Inject
import javax.inject.Provider

class WidgetProvider
@Inject
constructor(
        widgetRegistrator: ToProviderRegistrator
) {

    interface ToProviderRegistrator {
        fun registerWidgetToProvider(registerFunc: (id: Int, widgetContentProvider: Provider<out WidgetContent>) -> Unit)
    }

    // TODO use KClass
    private val widgets = mutableMapOf<Int, Provider<out WidgetContent>>()

    init {
        widgetRegistrator.registerWidgetToProvider { id, widgetContentProvider -> widgets.put(id, widgetContentProvider) }
    }

    fun getWidget(id: Int) = widgets.get(id)?.get()

}