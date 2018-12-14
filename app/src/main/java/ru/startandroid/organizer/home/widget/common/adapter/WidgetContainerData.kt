package ru.startandroid.organizer.home.widget.common.adapter

import android.net.Uri

data class WidgetContainerData(
        val title: String? = "",
        val settingsUri: Uri? = null,
        val refreshButtonIsVisible: Boolean = false,
        val settingsButtonIsVisible: Boolean = false,
        val closeButtonIsVisible: Boolean = false
)