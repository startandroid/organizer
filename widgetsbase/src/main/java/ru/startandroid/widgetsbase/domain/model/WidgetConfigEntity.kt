package ru.startandroid.widgetsbase.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

interface WidgetConfig : Parcelable

@Parcelize
data class WidgetConfigEntity(
        val id: Int,
        val config: WidgetConfig,
        val enabled: Boolean = true
) : Parcelable