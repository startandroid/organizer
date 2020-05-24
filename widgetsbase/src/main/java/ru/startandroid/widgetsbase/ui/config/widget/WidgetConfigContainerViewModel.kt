package ru.startandroid.widgetsbase.ui.config.widget

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.startandroid.device.SingleLiveEvent
import ru.startandroid.device.setIfNotExists
import ru.startandroid.widgetsbase.data.db.model.UpdateIntervals
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadataRepository
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import ru.startandroid.widgetsbase.domain.model.WidgetMainConfig
import ru.startandroid.widgetsbase.domain.usecase.GetWidgetConfigUseCase
import ru.startandroid.widgetsbase.domain.usecase.UpdateWidgetConfigUseCase

class WidgetConfigContainerViewModel(
        private val widgetId: Int,
        private val getWidgetConfigUseCase: GetWidgetConfigUseCase,
        private val updateIntervals: UpdateIntervals,
        private val updateWidgetConfigUseCase: UpdateWidgetConfigUseCase,
        private val widgetMetadataRepository: WidgetMetadataRepository,
        private val savedStateHandle: SavedStateHandle

) : ViewModel() {

    companion object {
        const val CODE_DIALOG_SAVE_CONFIG = 1
    }

    val title = ObservableField<Int>()
    val description = ObservableField<Int>()
    val enabled = savedStateHandle.getLiveData<Boolean>("enabled")
    val updateInterval = savedStateHandle.getLiveData<Int>("updateInterval")

    val showDialog = SingleLiveEvent<Int>()
    val closeScreen = SingleLiveEvent<Unit>()

    private val widgetConfig = MutableLiveData<WidgetConfigEntity?>()

    private val compositeDisposable = CompositeDisposable()

    private var isClosing = false

    init {
        readWidgetMetadata()
        readWidgetConfigFromDb()
    }

    fun getWidgetConfigEntity(): LiveData<out WidgetConfigEntity?> {
        return widgetConfig
    }

    private fun readWidgetMetadata() {
        widgetMetadataRepository.getWidgetMetadata(widgetId).let {
            title.set(it.details.titleResId)
            description.set(it.details.descriptionResId)
        }
    }

    private fun readWidgetConfigFromDb() {
        compositeDisposable.add(getWidgetConfigUseCase.invoke(widgetId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    savedStateHandle.setIfNotExists("enabled", it.mainConfig.enabled)
                    savedStateHandle.setIfNotExists("updateInterval", updateIntervals.indexOfInterval(it.mainConfig.updateInterval))
                    widgetConfig.value = it
                }, { })
        )
    }

    fun onBackPressed(newConfig: WidgetConfig): Boolean {
        if (isClosing) return false
        if (configWasChanged(newConfig)) {
            showSaveDialog()
            return true
        }
        return false
    }

    fun onSaveButtonPressed(newConfig: WidgetConfig) {
        saveConfigAndCloseScreen(newConfig)
    }

    fun onSaveDialogPositive(newConfig: WidgetConfig) {
        saveConfigAndCloseScreen(newConfig)
    }

    fun onSaveDialogNegative() {
        closeScreen()
    }

    private fun closeScreen() {
        isClosing = true
        closeScreen.call()
    }

    private fun configWasChanged(newConfig: WidgetConfig): Boolean {
        return (widgetConfig.value?.config != newConfig ||
                widgetConfig.value?.mainConfig?.enabled != enabled.value ||
                widgetConfig.value?.mainConfig?.updateInterval != updateIntervals.getInterval(updateInterval.value)
                )
    }

    private fun showSaveDialog() {
        showDialog.value = CODE_DIALOG_SAVE_CONFIG
    }

    private fun saveConfigAndCloseScreen(newConfig: WidgetConfig) {
        updateWidgetConfigUseCase.invoke(
                WidgetConfigEntity(widgetId, newConfig,
                        WidgetMainConfig(enabled.value ?: false, updateIntervals.getInterval(updateInterval.value))
                )
        ).subscribe()
        closeScreen()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
