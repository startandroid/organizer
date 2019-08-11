package ru.startandroid.device.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

fun <T : ViewModel> Fragment.getViewModel(cls: Class<T>, factory: ViewModelProvider.Factory? = null): T {
    return factory?.let {
        ViewModelProviders.of(this, it).get(cls)
    } ?: ViewModelProviders.of(this).get(cls)
}