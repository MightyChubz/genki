package com.theroughstallions.genki.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.theroughstallions.genki.R
import com.theroughstallions.genki.listItems

class HomeViewModel(private val itemList: ArrayList<listItems>) : RecyclerView.Adapter<HomeViewModel.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item,
            parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.listTitle.text = currentItem.listItem
        holder.listInfo.text = currentItem.listInfo
        holder.listQuantity.text = currentItem.listQuantity
        holder.listPrice.text = currentItem.listPrice
        holder.listTotal.text = currentItem.listTotal
    }

    override fun getItemCount(): Int {

        return itemList.size
    }
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val listTitle : TextView = itemView.findViewById<TextView>(R.id.listText)
        val listInfo : TextView = itemView.findViewById<TextView>(R.id.listInfo)
        val listQuantity : TextView = itemView.findViewById<TextView>(R.id.listQuantity)
        val listPrice : TextView = itemView.findViewById<TextView>(R.id.listPrice)
        val listTotal: TextView = itemView.findViewById<TextView>(R.id.listTotal)
    }




}