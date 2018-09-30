package ru.startandroid.organizer.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_home.view.*
import ru.startandroid.domain.WidgetEntity
import ru.startandroid.organizer.R
import ru.startandroid.organizer.objects.SimpleObject

class HomeFragmentRvAdapter(private val data: List<WidgetEntity>) : RecyclerView.Adapter<HomeFragmentRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFragmentRvAdapter.ViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.fragment_home_rv_item, parent, false) as View
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: HomeFragmentRvAdapter.ViewHolder, position: Int) {
        holder.bindItems(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        var viewItemTvName: TextView = viewItem.findViewById(R.id.tv_name)

        fun bindItems(data: WidgetEntity) {
            viewItemTvName.text = data.data
        }
    }
}