package ru.startandroid.widgetsbase.ui.config.widget

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.startandroid.widgetsbase.data.metadata.WidgetConfigScreenMetadataRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository
import javax.inject.Inject

class WidgetConfigContainerViewModelFactory @Inject constructor(
        private val widgetConfigRepository: WidgetConfigRepository,
        private val widgetMetadataRepositoryImpl: WidgetConfigScreenMetadataRepository,
        private val widgetId: Int
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        Log.d("qweee", "factory ${widgetId}")
        return when (modelClass) {
            WidgetConfigContainerViewModel::class.java -> WidgetConfigContainerViewModel(widgetId, widgetConfigRepository, widgetMetadataRepositoryImpl) as T
            else -> super.create(modelClass)
        }
    }

}