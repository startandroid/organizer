package ru.startandroid.device

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle

fun <T> SavedStateHandle.setIfNotExists(key: String, value: T) {
    if (!this.contains(key)) {
        this.set(key, value)
    }
}

fun <T> MutableLiveData<T>.updateValue(defaultValue: T, update: (currentValue: T) -> T) {
    val currentValue = this.value ?: defaultValue
    val newValue = update(currentValue)
    this.value = newValue
}
