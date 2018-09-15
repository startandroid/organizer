package ru.startandroid.organizer.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_home.view.*
import ru.startandroid.organizer.R
import ru.startandroid.organizer.objects.SimpleObject

 class HomeFragmentRvAdapter(val objList: ArrayList<SimpleObject>) : RecyclerView.Adapter<HomeFragmentRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFragmentRvAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.fragment_home_rv_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: HomeFragmentRvAdapter.ViewHolder, position: Int) {
        holder.bindItems(objList[position])
    }

    override fun getItemCount(): Int {
        return objList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(data: SimpleObject) {
            val textViewName = itemView.findViewById(R.id.tv_name) as TextView
            textViewName.text = data.name
        }
    }
}