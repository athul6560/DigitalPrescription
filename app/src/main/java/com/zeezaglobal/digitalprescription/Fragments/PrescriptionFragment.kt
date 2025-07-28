package com.zeezaglobal.digitalprescription.Fragments


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.zeezaglobal.digitalprescription.Activities.MedicalHistoryActivity
import com.zeezaglobal.digitalprescription.Adapter.PrescriptionAdapter
import com.zeezaglobal.digitalprescription.Entity.Patient

import com.zeezaglobal.digitalprescription.R
import com.zeezaglobal.digitalprescription.Utils.AgeUtils
import com.zeezaglobal.digitalprescription.Utils.Constants
import com.zeezaglobal.digitalprescription.ViewModel.PrescriptionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrescriptionFragment : Fragment() {


    private lateinit var generateButton: Button

    private lateinit var prescriptionRecyclerView: RecyclerView
    private lateinit var adapter: PrescriptionAdapter
    private lateinit var patientName: TextView
    private lateinit var phoneNumber: TextView
    private lateinit var ageText: TextView
    private lateinit var viewHistory: TextView

    private var patient: Patient? = null


    companion object {
        fun newInstance() = PrescriptionFragment()
    }

    private val viewModel: PrescriptionViewModel by activityViewModels()
    override fun onResume() {
        super.onResume()
        // This code will run every time the fragment becomes visible
        patient = Constants.currentPatient
        // Toast.makeText(requireContext(), patient?.firstName + "", Toast.LENGTH_SHORT).show()


        // You can reinitialize or refresh any data that needs to be updated here
        if (patient != null) {
            patientName.text = patient?.firstName
            phoneNumber.text = patient?.contactNumber
            ageText.text = AgeUtils.calculateAge(patient?.dateOfBirth)
        } else {
            patientName.text = "No Patient Selected"
            phoneNumber.text = ""
            ageText.text=""
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        patient = Constants.currentPatient
        // Toast.makeText(requireContext(), patient?.firstName + "", Toast.LENGTH_SHORT).show()

        // TODO: Use the ViewModel if needed
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_prescription, container, false)


        generateButton = view.findViewById(R.id.button4)

        patientName = view.findViewById(R.id.patient_name)
        phoneNumber = view.findViewById(R.id.phone_number_text)
        ageText = view.findViewById(R.id.age_text)

        viewHistory = view.findViewById(R.id.view_history)
        val floatingActionButton = view.findViewById<ConstraintLayout>(R.id.floatingActionBtn)

        floatingActionButton.setOnClickListener {
            Toast.makeText(requireContext(), "FAB clicked", Toast.LENGTH_SHORT).show()
            // Navigate or open a dialog, etc.
        }
        prescriptionRecyclerView = view.findViewById(R.id.prescription_rv)
        prescriptionRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        // Dummy data
        prescriptionRecyclerView.layoutManager = LinearLayoutManager(requireContext())



        adapter = PrescriptionAdapter(emptyList())
        prescriptionRecyclerView.adapter = adapter


        if (patient != null) {
            patientName.text = patient?.firstName
            phoneNumber.text = patient?.contactNumber
            ageText.text = AgeUtils.calculateAge(patient?.dateOfBirth)

            // Trigger fetching prescriptions
            viewModel.fetchPrescriptionsForPatient(patient!!.id)

            // Observe prescriptions LiveData once
            viewModel.prescriptions.observe(viewLifecycleOwner) { prescriptions ->

                adapter.updateData(prescriptions)
            }
        } else {
            patientName.text = "No Patient Selected"
            phoneNumber.text = ""
            ageText.text = ""
        }

        viewHistory.setOnClickListener{
            val intent = Intent(requireContext(), MedicalHistoryActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}
