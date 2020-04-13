package ru.startandroid.widgetsbase.ui.config.widget

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.startandroid.device.SingleLiveEvent
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
        widgetMetadataRepository: WidgetMetadataRepository

) : ViewModel() {

    companion object {
        const val CODE_DIALOG_SAVE_CONFIG = 1
    }


    private var widgetConfigEntity: WidgetConfigEntity? = null
        set(value) {
            field = value
            value?.let {
                enabled.set(it.mainConfig.enabled)
                updateInterval.set(updateIntervals.indexOfInterval(it.mainConfig.updateInterval))
            }
        }

    val title = ObservableField<Int>()
    val description = ObservableField<Int>()
    val enabled = ObservableBoolean()
    val updateInterval = ObservableField<Int>()

    val showDialog = SingleLiveEvent<Int>()
    val closeScreen = SingleLiveEvent<Unit>()

    private var isClosing = false

    init {
        widgetMetadataRepository.getWidgetMetadata(widgetId).let {
            title.set(it.details.titleResId)
            description.set(it.details.descriptionResId)
        }
    }

    fun getWidgetConfigEntity(): LiveData<out WidgetConfigEntity?> {
        val widgetConfigEntityFlowable = if (widgetConfigEntity == null) {
            readWidgetConfigFromDb()
        } else {
            Single.just(widgetConfigEntity)
        }
        return LiveDataReactiveStreams.fromPublisher(widgetConfigEntityFlowable.toFlowable())
    }

    private fun readWidgetConfigFromDb(): Single<WidgetConfigEntity> {
        return getWidgetConfigUseCase.invoke(widgetId)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    widgetConfigEntity = it
                }
    }

    fun onBackPressed(newConfig: WidgetConfig): Boolean {
        if (isClosing) return false
        Log.d("qweee", "onBackPressed new config $newConfig, old config ${widgetConfigEntity?.config}, equals: ${newConfig == widgetConfigEntity?.config}")
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
        Log.d("qweee", "close screen")
        isClosing = true
        closeScreen.call()
    }

    private fun configWasChanged(newConfig: WidgetConfig): Boolean {
        return (widgetConfigEntity?.config != newConfig ||
                widgetConfigEntity?.mainConfig?.enabled != enabled.get() ||
                widgetConfigEntity?.mainConfig?.updateInterval != updateIntervals.getInterval(updateInterval.get())
                )
    }

    private fun showSaveDialog() {
        showDialog.value = CODE_DIALOG_SAVE_CONFIG
    }

    private fun saveConfigAndCloseScreen(newConfig: WidgetConfig) {
        updateWidgetConfigUseCase.invoke(WidgetConfigEntity(widgetId, newConfig, WidgetMainConfig(enabled.get(), updateIntervals.getInterval(updateInterval.get())))).subscribe()
        closeScreen()
    }


}
