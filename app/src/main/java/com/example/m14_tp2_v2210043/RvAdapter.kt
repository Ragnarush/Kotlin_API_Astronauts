package com.example.m14_tp2_v2210043

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RvAdapter(val result:Resultats) : RecyclerView.Adapter<RvAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.apply {
            findViewById<TextView>(R.id.tvNameItem).text = result.people[position].name
            findViewById<TextView>(R.id.tvCraftItem).text = result.people[position].craft
        }
    }

    override fun getItemCount(): Int {
        return result.people.count()
    }
}