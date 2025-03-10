package com.zeezaglobal.digitalprescription.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zeezaglobal.digitalprescription.Entity.Patient
import com.zeezaglobal.digitalprescription.R

class PatientAdapter(private val patients: MutableList<Patient>, private val listener: OnPatientClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface OnPatientClickListener {
        fun onPatientClick(patient: Patient)
    }
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    fun addPatients(newPatients: List<Patient>) {
        patients.addAll(newPatients)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (patients[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.patient_layout, parent, false)
            PatientViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is PatientViewHolder) {
            val patient = patients[position]
            val initials = patient.firstName.take(2).uppercase()
            holder.avatar.text = initials
            holder.avatar.text = initials.uppercase()
            holder.firstName.text = patient.firstName
            holder.itemView.setOnClickListener {
                listener.onPatientClick(patient)
            }

        }
    }
    fun updatePatients(newPatients: List<Patient>) {

        patients.clear()

        patients.addAll(newPatients)
        notifyDataSetChanged() // Ensures UI refresh
    }

    override fun getItemCount(): Int = patients.size

    inner class PatientViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val firstName: TextView = view.findViewById(R.id.name_edittext)
        val avatar: TextView = view.findViewById(R.id.tvAvatar)
    }

    inner class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view)
}