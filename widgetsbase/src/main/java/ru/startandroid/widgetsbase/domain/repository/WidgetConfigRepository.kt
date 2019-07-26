package ru.startandroid.widgetsbase.domain.repository

import io.reactivex.Single
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity

interface WidgetConfigRepository {

    fun updateOrInsertSync(widgetConfigEntity: WidgetConfigEntity): Long

    fun getByIdSync(id: Int): WidgetConfigEntity?

    fun getById(id: Int): Single<WidgetConfigEntity>

    fun update(id: Int, config: WidgetConfig, enabled: Boolean): Single<Int>

}