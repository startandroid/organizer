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
import ru.startandroid.widgetsbase.data.metadata.WidgetConfigScreenMetadataRepository
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository

class WidgetConfigContainerViewModel(
        private val widgetId: Int,
        private val widgetConfigRepository: WidgetConfigRepository,
        widgetMetadataRepositoryImpl: WidgetConfigScreenMetadataRepository

) : ViewModel() {

    companion object {
        const val CODE_DIALOG_SAVE_CONFIG = 1
    }

    private var widgetConfigEntity: WidgetConfigEntity? = null
        set(value) {
            field = value
            value?.let {
                enabled.set(it.enabled)
            }
        }

    val title = ObservableField<Int>()
    val description = ObservableField<Int>()
    val enabled = ObservableBoolean()

    val showDialog = SingleLiveEvent<Int>()
    val closeScreen = SingleLiveEvent<Unit>()

    private var isClosing = false

    init {
        title.set(widgetMetadataRepositoryImpl.getWidgetTitleResId(widgetId))
        description.set(widgetMetadataRepositoryImpl.getWidgetDescriptionResId(widgetId))
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
        if (configWasChanged(newConfig, enabled.get())) {
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

    private fun configWasChanged(newConfig: WidgetConfig, enabled: Boolean): Boolean {
        return (widgetConfigEntity?.config != newConfig || widgetConfigEntity?.enabled != enabled)
    }

    private fun showSaveDialog() {
        showDialog.value = CODE_DIALOG_SAVE_CONFIG
    }

    private fun saveConfigAndCloseScreen(newConfig: WidgetConfig) {
        widgetConfigRepository.update(widgetId, newConfig, enabled.get())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        closeScreen()
    }

}