package ru.startandroid.widgetsbase.ui.config.widget

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.startandroid.widgetsbase.data.metadata.WidgetConfigScreenMetadataRepository
import ru.startandroid.widgetsbase.data.metadata.WidgetRefreshParametersMetadataRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository
import ru.startandroid.widgetsbase.domain.usecase.UpdateWidgetUseCase
import javax.inject.Inject

class WidgetConfigContainerViewModelFactory @Inject constructor(
        private val widgetConfigRepository: WidgetConfigRepository,
        private val widgetMetadataRepositoryImpl: WidgetConfigScreenMetadataRepository,
        private val widgetRefreshParametersMetadataRepository: WidgetRefreshParametersMetadataRepository,
        private val updateIntervals: UpdateIntervals,
        private val updateWidgetUseCase: UpdateWidgetUseCase,
        private val widgetId: Int
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        Log.d("qweee", "factory ${widgetId}")
        return when (modelClass) {
            WidgetConfigContainerViewModel::class.java -> WidgetConfigContainerViewModel(widgetId, widgetConfigRepository, updateIntervals, updateWidgetUseCase, widgetMetadataRepositoryImpl, widgetRefreshParametersMetadataRepository) as T
            else -> super.create(modelClass)
        }
    }

}