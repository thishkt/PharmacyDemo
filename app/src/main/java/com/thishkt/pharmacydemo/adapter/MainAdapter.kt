package com.thishkt.pharmacydemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.thishkt.pharmacydemo.R
import com.thishkt.pharmacydemo.data.Feature


class MainAdapter(private val itemClickListener: IItemClickListener) :
    RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    var pharmacyList: List<Feature> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvName.text = pharmacyList[position].property.name
        holder.tvAdultAmount.text = pharmacyList[position].property.mask_adult
        holder.tvChildAmount.text = pharmacyList[position].property.mask_child
        holder.layoutItem.setOnClickListener {
            itemClickListener.onItemClickListener(pharmacyList[position])
        }
    }

    override fun getItemCount(): Int {
        return pharmacyList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvAdultAmount: TextView = itemView.findViewById(R.id.tv_adult_amount)
        val tvChildAmount: TextView = itemView.findViewById(R.id.tv_child_amount)
        val layoutItem: ConstraintLayout = itemView.findViewById(R.id.layout_item)
    }

    interface IItemClickListener {
        fun onItemClickListener(data: Feature)
    }

}