package ru.startandroid.widgetsbase.ui.config.list.adapter

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.config_item.*
import ru.startandroid.device.SingleLiveEvent

class ConfigListHolder(override val containerView: View,
                       onClickClb: SingleLiveEvent<Int>,
                       onEnableClb: SingleLiveEvent<Pair<Int, Boolean>>
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    init {
        containerView.setOnClickListener {
            onClickClb.value = widgetId
        }
        tgEnabled.setOnCheckedChangeListener { buttonView, isChecked ->
            Log.d("qweee", "$widgetId $isChecked")
            onEnableClb.value = widgetId to isChecked
        }
    }

    private var widgetId = 0

    fun bind(configListItem: ConfigListItem) {
        widgetId = configListItem.id
        tvTitle.text = "${configListItem.id} ${configListItem.title}"
        tgEnabled.setCheckedQuiet(configListItem.enabled)
    }

}