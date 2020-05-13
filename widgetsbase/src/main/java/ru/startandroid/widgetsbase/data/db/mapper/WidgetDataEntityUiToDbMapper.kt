package ru.startandroid.widgetsbase.data.db.mapper

import com.google.gson.Gson
import ru.startandroid.domain.ScopeApplication
import ru.startandroid.domain.mapping.Mapper
import ru.startandroid.widgetsbase.data.db.model.WidgetDataEntityDb
import ru.startandroid.widgetsbase.domain.model.WidgetData
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import javax.inject.Inject

@ScopeApplication
class WidgetDataEntityUiToDbMapper @Inject constructor(
        private val gson: Gson
) : Mapper<WidgetDataEntity, WidgetDataEntityDb> {

    override fun map(input: WidgetDataEntity): WidgetDataEntityDb {
        val data = dataToJson(input.data)

        return WidgetDataEntityDb(input.id, data)
    }

    private fun dataToJson(data: WidgetData) = gson.toJson(data)

}