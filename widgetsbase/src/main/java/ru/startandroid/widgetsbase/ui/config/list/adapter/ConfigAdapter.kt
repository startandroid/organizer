package ru.startandroid.widgetsbase.ui.config.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.startandroid.device.SingleLiveEvent
import ru.startandroid.widgetsbase.R
import javax.inject.Inject


class ConfigAdapter @Inject constructor() : ListAdapter<Config, ConfigHolder>(diffCallback) {

    private val clicks = SingleLiveEvent<Int>()
    private val enables = SingleLiveEvent<Pair<Int, Boolean>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConfigHolder {
        return ConfigHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.config_item, parent, false),
                clicks, enables
        )
    }

    fun getClicks(): LiveData<Int> = clicks

    fun getEnables(): LiveData<Pair<Int, Boolean>> = enables

    override fun onBindViewHolder(holder: ConfigHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Config>() {
            override fun areItemsTheSame(oldItem: Config, newItem: Config): Boolean =
                    oldItem.id == newItem.id


            override fun areContentsTheSame(oldItem: Config, newItem: Config): Boolean =
                    oldItem.enabled == newItem.enabled && oldItem.title == newItem.title

        }
    }

}