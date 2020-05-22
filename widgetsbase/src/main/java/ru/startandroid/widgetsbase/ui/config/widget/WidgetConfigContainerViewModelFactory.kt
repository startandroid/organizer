package ru.startandroid.widgetsbase.ui.config.widget

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import ru.startandroid.widgetsbase.data.db.model.UpdateIntervals
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadataRepository
import ru.startandroid.widgetsbase.domain.usecase.GetWidgetConfigUseCase
import ru.startandroid.widgetsbase.domain.usecase.UpdateWidgetConfigUseCase
import javax.inject.Inject

class WidgetConfigContainerViewModelFactory @Inject constructor(
        fragment: Fragment,
        private val getWidgetConfigUseCase: GetWidgetConfigUseCase,
        private val widgetMetadataRepository: WidgetMetadataRepository,
        private val updateIntervals: UpdateIntervals,
        private val updateWidgetConfigUseCase: UpdateWidgetConfigUseCase,
        private val widgetId: Int
) : AbstractSavedStateViewModelFactory(fragment, Bundle.EMPTY) {
    override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
        return when (modelClass) {
            WidgetConfigContainerViewModel::class.java -> WidgetConfigContainerViewModel(widgetId, getWidgetConfigUseCase, updateIntervals, updateWidgetConfigUseCase, widgetMetadataRepository, handle) as T
            else -> super.create(modelClass)
        }
    }

}