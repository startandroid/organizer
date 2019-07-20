package ru.startandroid.widgetsbase

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


interface WidgetData

interface WidgetConfig : Parcelable

data class WidgetDataEntity(
        val id: Int,
        val data: WidgetData
)

@Parcelize
data class WidgetConfigEntity(
        val id: Int,
        val config: WidgetConfig,
        val enabled: Boolean = true
) : Parcelable






