package ru.startandroid.widgets

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


interface WidgetData

interface WidgetConfig: Parcelable

data class WidgetDataEntity(
        val id: Int,
        val data: WidgetData
)

data class WidgetConfigEntity(
        val id: Int,
        val config: WidgetConfig,
        val enabled: Boolean = true
)






