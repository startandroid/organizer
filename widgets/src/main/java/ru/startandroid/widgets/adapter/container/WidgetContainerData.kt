package ru.startandroid.widgets.adapter.container

data class WidgetContainerData(
        val id: Int = 0,
        val title: String? = "",
        val refreshButtonIsVisible: Boolean = false,
        val settingsButtonIsVisible: Boolean = false,
        val closeButtonIsVisible: Boolean = false
)