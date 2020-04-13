package ru.startandroid.widgetsbase.ui.widgets

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import ru.startandroid.device.Navigator
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import ru.startandroid.widgetsbase.domain.repository.WidgetDataRepository
import ru.startandroid.widgetsbase.domain.usecase.GetEnabledWidgetsUseCase

class WidgetsViewModel(
        private val getEnabledWidgetsUseCase: GetEnabledWidgetsUseCase,
        private val navigator: Navigator
) : ViewModel() {

    private val widgetsLiveData by lazy {
        LiveDataReactiveStreams.fromPublisher(getEnabledWidgetsUseCase.invoke())
    }

    fun widgets(): LiveData<List<WidgetDataEntity>> = widgetsLiveData

    fun onConfigButtonClick() {
        navigator.openWidgetsConfig()
    }

}

