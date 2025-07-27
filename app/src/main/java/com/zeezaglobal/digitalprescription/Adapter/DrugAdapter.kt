package com.zeezaglobal.digitalprescription.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zeezaglobal.digitalprescription.Entity.Drug
import com.zeezaglobal.digitalprescription.R

class DrugAdapter(private val drugs: List<Drug>) :
    RecyclerView.Adapter<DrugAdapter.DrugViewHolder>() {

    inner class DrugViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.drug_name)
        val type: TextView = itemView.findViewById(R.id.drug_type)
        val description: TextView = itemView.findViewById(R.id.drug_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrugViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_drug, parent, false)
        return DrugViewHolder(view)
    }

    override fun getItemCount(): Int = drugs.size

    override fun onBindViewHolder(holder: DrugViewHolder, position: Int) {
        val drug = drugs[position]
        holder.name.text = drug.name
        holder.type.text = "${drug.type} - ${drug.type_name}"
        holder.description.text = drug.description
    }
}
