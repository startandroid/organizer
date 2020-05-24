package ru.startandroid.organizer.exchange.presentation.widget.config

import kotlinx.android.parcel.Parcelize
import ru.startandroid.widgetsbase.domain.model.WidgetConfig

@Parcelize
data class ExchangeWidgetConfig(
        val rates: List<Pair<String, String>> = emptyList(),
        val showReverse: Boolean = false
) : WidgetConfig