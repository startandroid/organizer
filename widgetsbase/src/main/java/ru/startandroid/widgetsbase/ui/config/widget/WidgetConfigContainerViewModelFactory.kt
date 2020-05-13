package ru.startandroid.widgetsbase.ui.config.widget

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.startandroid.widgetsbase.data.db.model.UpdateIntervals
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadataRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository
import ru.startandroid.widgetsbase.domain.usecase.GetWidgetConfigUseCase
import ru.startandroid.widgetsbase.domain.usecase.UpdateWidgetConfigUseCase
import javax.inject.Inject

class WidgetConfigContainerViewModelFactory @Inject constructor(
        private val getWidgetConfigUseCase: GetWidgetConfigUseCase,
        private val widgetMetadataRepository: WidgetMetadataRepository,
        private val updateIntervals: UpdateIntervals,
        private val updateWidgetConfigUseCase: UpdateWidgetConfigUseCase,
        private val widgetId: Int
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            WidgetConfigContainerViewModel::class.java -> WidgetConfigContainerViewModel(widgetId, getWidgetConfigUseCase, updateIntervals, updateWidgetConfigUseCase, widgetMetadataRepository) as T
            else -> super.create(modelClass)
        }
    }

}