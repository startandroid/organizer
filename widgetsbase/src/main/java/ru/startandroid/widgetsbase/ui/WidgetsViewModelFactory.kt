package ru.startandroid.widgetsbase.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.startandroid.device.Navigator
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetDataRepository
import ru.startandroid.widgetsbase.domain.usecase.DisableWidgetUseCase
import ru.startandroid.widgetsbase.domain.usecase.EnableWidgetUseCase
import ru.startandroid.widgetsbase.domain.usecase.GetConfigListUseCase
import ru.startandroid.widgetsbase.domain.usecase.GetEnabledWidgetsUseCase
import ru.startandroid.widgetsbase.ui.config.list.ConfigListViewModel
import ru.startandroid.widgetsbase.ui.widgets.WidgetsViewModel
import javax.inject.Inject

class WidgetsViewModelFactory @Inject constructor(
        private val getEnabledWidgetsUseCase: GetEnabledWidgetsUseCase,
        private val enableWidgetUseCase: EnableWidgetUseCase,
        private val disableWidgetUseCase: DisableWidgetUseCase,
        private val getConfigListUseCase: GetConfigListUseCase,
        private val navigator: Navigator
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            WidgetsViewModel::class.java -> WidgetsViewModel(getEnabledWidgetsUseCase, navigator) as T
            ConfigListViewModel::class.java -> ConfigListViewModel(disableWidgetUseCase, enableWidgetUseCase, getConfigListUseCase, navigator) as T
            else -> super.create(modelClass)
        }
    }

}