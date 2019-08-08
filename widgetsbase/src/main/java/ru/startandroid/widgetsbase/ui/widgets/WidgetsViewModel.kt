package ru.startandroid.widgetsbase.ui.widgets

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.startandroid.device.Navigator
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import ru.startandroid.widgetsbase.domain.repository.WidgetDataRepository
import javax.inject.Inject

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

class WidgetsViewModelFactory @Inject constructor(
        private val widgetsRepository: WidgetDataRepository,
        private val navigator: Navigator
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == WidgetsViewModel::class.java) {
            return WidgetsViewModel(widgetsRepository, navigator) as T
        }
        return super.create(modelClass)
    }

}