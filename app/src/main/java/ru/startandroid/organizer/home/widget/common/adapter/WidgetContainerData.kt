package ru.startandroid.organizer.home.widget.common.adapter

data class WidgetContainerData(
        val id: Int = 0,
        val title: String? = "",
        val refreshButtonIsVisible: Boolean = false,
        val settingsButtonIsVisible: Boolean = false,
        val closeButtonIsVisible: Boolean = false
)