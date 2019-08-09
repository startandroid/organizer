package ru.startandroid.widgetsbase.ui.widgets

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.startandroid.device.Navigator
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import ru.startandroid.widgetsbase.domain.repository.WidgetDataRepository

class WidgetsViewModel(
        private val widgetsRepository: WidgetDataRepository,
        private val navigator: Navigator
) : ViewModel() {

    private val widgetsLiveData by lazy {
        widgetsRepository.getEnabledWidgets()
    }

    fun widgets(): LiveData<List<WidgetDataEntity>> = widgetsLiveData

    fun onConfigButtonClick() {
        navigator.openWidgetsConfig()
    }

}

