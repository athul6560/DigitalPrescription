package com.zeezaglobal.digitalprescription.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zeezaglobal.digitalprescription.Entity.Prescription
import com.zeezaglobal.digitalprescription.R

class PrescriptionAdapter(
    private val prescriptions: List<Prescription>
) : RecyclerView.Adapter<PrescriptionAdapter.PrescriptionViewHolder>() {

    inner class PrescriptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPatientName: TextView = itemView.findViewById(R.id.tvPatientName)
        val tvDoctorName: TextView = itemView.findViewById(R.id.tvDoctorName)
        val tvCreatedDate: TextView = itemView.findViewById(R.id.tvCreatedDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrescriptionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_prescription, parent, false)
        return PrescriptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: PrescriptionViewHolder, position: Int) {
        val prescription = prescriptions[position]
        holder.tvPatientName.text = prescription.patientName
        holder.tvDoctorName.text = prescription.doctorName
        holder.tvCreatedDate.text = prescription.createdDate
    }

    override fun getItemCount(): Int = prescriptions.size
}