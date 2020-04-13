package ru.startandroid.widgetsbase.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.startandroid.widgetsbase.data.db.model.UpdateInterval

@Parcelize
data class WidgetConfigEntity(
        val id: Int,
        val config: WidgetConfig,
        val mainConfig: WidgetMainConfig
) : Parcelable

interface WidgetConfig : Parcelable

@Parcelize
data class WidgetMainConfig(
        val enabled: Boolean = true,
        val updateInterval: UpdateInterval = UpdateInterval.MINUTES_15
): Parcelable