package ru.startandroid.widgetsbase.ui.config.widget

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.startandroid.device.SingleLiveEvent
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadataRepository
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository
import ru.startandroid.widgetsbase.domain.usecase.UpdateWidgetUseCase
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WidgetConfigContainerViewModel(
        private val widgetId: Int,
        private val widgetConfigRepository: WidgetConfigRepository,
        private val updateIntervals: UpdateIntervals,
        private val updateWidgetUseCase: UpdateWidgetUseCase,
        widgetMetadataRepository: WidgetMetadataRepository

) : ViewModel() {

    companion object {
        const val CODE_DIALOG_SAVE_CONFIG = 1
    }


    private var widgetConfigEntity: WidgetConfigEntity? = null
        set(value) {
            field = value
            value?.let {
                enabled.set(it.enabled)
                updateInterval.set(updateIntervals.indexOfInterval(it.updateInterval))
            }
        }

    val title = ObservableField<Int>()
    val description = ObservableField<Int>()
    val enabled = ObservableBoolean()
    val updateInterval = ObservableField<Int>()
    val updateIntervalVisible = ObservableBoolean()

    val showDialog = SingleLiveEvent<Int>()
    val closeScreen = SingleLiveEvent<Unit>()

    private var isClosing = false

    init {
        title.set(widgetMetadataRepository.getWidgetMetadata(widgetId)?.details?.titleResId)
        description.set(widgetMetadataRepository.getWidgetMetadata(widgetId)?.details?.descriptionResId)
        updateIntervalVisible.set(widgetMetadataRepository.getWidgetMetadata(widgetId)?.update?.autoRefresh
                ?: false)
    }

    fun getWidgetConfigEntity(): LiveData<out WidgetConfigEntity?> {
        val widgetConfigEntityFlowable = if (widgetConfigEntity == null) {
            readWidgetConfigFromDb().toFlowable()
        } else {
            Flowable.just(widgetConfigEntity)
        }
        return LiveDataReactiveStreams.fromPublisher(widgetConfigEntityFlowable)
    }

    private fun readWidgetConfigFromDb(): Single<WidgetConfigEntity> {
        return widgetConfigRepository.getById(widgetId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
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
                widgetConfigEntity?.enabled != enabled.get() ||
                widgetConfigEntity?.updateInterval != updateIntervals.getInterval(updateInterval.get())
                )
    }

    private fun showSaveDialog() {
        showDialog.value = CODE_DIALOG_SAVE_CONFIG
    }

    private fun saveConfigAndCloseScreen(newConfig: WidgetConfig) {
        updateWidgetUseCase.invoke(WidgetConfigEntity(widgetId, newConfig, enabled.get(), updateIntervals.getInterval(updateInterval.get())))
        closeScreen()
    }

}

class UpdateIntervals @Inject constructor() {

    private val intervalValues = listOf<Long>(
            0,
            TimeUnit.MINUTES.toMillis(15),
            TimeUnit.MINUTES.toMillis(30),
            TimeUnit.HOURS.toMillis(1),
            TimeUnit.HOURS.toMillis(3),
            TimeUnit.HOURS.toMillis(12),
            TimeUnit.HOURS.toMillis(24)
    )

    fun getInterval(index: Int?) =
            intervalValues[index ?: 0]

    fun indexOfInterval(value: Long) =
            intervalValues.indexOf(value)
}