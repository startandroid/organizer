package ru.startandroid.device.delegation

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlin.reflect.KProperty


fun <T : ViewModel> viewModel(cls: Class<T>, factory: (() -> ViewModelProvider.Factory?)? = null) = ViewModelDelegate(cls, factory)

class ViewModelDelegate<T : ViewModel>(val cls: Class<T>, val factory: (() -> ViewModelProvider.Factory?)? = null) {

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if (thisRef !is Fragment) throw Exception("ViewModelDelegate should be used only in fragments")
        return factory?.invoke()?.let {
            ViewModelProvider(thisRef, it).get(cls)
            } ?: ViewModelProvider(thisRef).get(cls)
    }
}
