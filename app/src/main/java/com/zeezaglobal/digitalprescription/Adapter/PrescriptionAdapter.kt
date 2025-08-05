package com.zeezaglobal.digitalprescription.Adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zeezaglobal.digitalprescription.Entity.Prescription
import com.zeezaglobal.digitalprescription.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class PrescriptionAdapter(private var prescriptions: List<Prescription>) :
    RecyclerView.Adapter<PrescriptionAdapter.PrescriptionViewHolder>() {

    inner class PrescriptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val prescribedDate: TextView = itemView.findViewById(R.id.prescribed_date)
        val remark: TextView = itemView.findViewById(R.id.remark)
        val drugRecyclerView: RecyclerView = itemView.findViewById(R.id.drug_recycler_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrescriptionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_prescription, parent, false)
        return PrescriptionViewHolder(view)
    }
    fun updateData(newPrescriptions: List<Prescription>) {
        prescriptions = newPrescriptions
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int = prescriptions.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: PrescriptionViewHolder, position: Int) {
        val prescription = prescriptions[position]
        val inputDate = LocalDate.parse(prescription.prescribedDate) // e.g., "2025-07-24"
        val outputFormatter = DateTimeFormatter.ofPattern("d MMM, yyyy", Locale.ENGLISH)
        val formattedDate = inputDate.format(outputFormatter)

        holder.prescribedDate.text = formattedDate
       // holder.prescribedDate.text = prescription.prescribedDate
        holder.remark.text = prescription.remarks

        holder.drugRecyclerView.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.drugRecyclerView.adapter = DrugAdapter(prescription.prescribedDrugs)
        if (holder.drugRecyclerView.itemDecorationCount == 0) {
            val divider = DividerItemDecoration(holder.drugRecyclerView.context, DividerItemDecoration.VERTICAL)
            ContextCompat.getDrawable(holder.drugRecyclerView.context, R.drawable.recycler_divider)?.let {
                divider.setDrawable(it)
            }
            holder.drugRecyclerView.addItemDecoration(divider)
        }
    }
}
