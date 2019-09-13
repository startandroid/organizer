package ru.startandroid.widgetsbase.ui.config.list

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import ru.startandroid.device.Navigator
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository
import ru.startandroid.widgetsbase.domain.usecase.DisableWidgetUseCase
import ru.startandroid.widgetsbase.domain.usecase.EnableWidgetUseCase
import ru.startandroid.widgetsbase.ui.config.list.adapter.Config

class WidgetsConfigViewModel(
        private val widgetConfigRepository: WidgetConfigRepository,
        private val disableWidgetUseCase: DisableWidgetUseCase,
        private val enableWidgetUseCase: EnableWidgetUseCase,
        private val navigator: Navigator
) : ViewModel() {

    private val widgetsConfigLiveData by lazy {
        LiveDataReactiveStreams.fromPublisher(
                widgetConfigRepository.getAll()
                        .map {
                            it.map {
                                Config(it.id, "title ${it.id}", it.enabled)
                            }
                        }
        )
    }

    fun getConfigs() = widgetsConfigLiveData

    fun onItemClick(id: Int) {
        navigator.openWidgetConfig(id)
    }

    fun onItemEnabled(id: Int, enabled: Boolean) {
        if (enabled) {
            enableWidgetUseCase.invoke(id)
        } else {
            disableWidgetUseCase.invoke(id)
        }
    }


}