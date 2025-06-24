package com.zeezaglobal.digitalprescription.Fragments


import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.zeezaglobal.digitalprescription.Activities.MedicalHistoryActivity
import com.zeezaglobal.digitalprescription.Activities.PdfActivity
import com.zeezaglobal.digitalprescription.Entity.Patient

import com.zeezaglobal.digitalprescription.R
import com.zeezaglobal.digitalprescription.Utils.AgeUtils
import com.zeezaglobal.digitalprescription.Utils.Constants

class PrescriptionFragment : Fragment() {
    private lateinit var searchEditText: AutoCompleteTextView
    private lateinit var durationRadioGroup: RadioGroup
    private lateinit var generateButton: Button
    private lateinit var drugName: TextView
    private lateinit var remarks: EditText
    private lateinit var patientName: TextView
    private lateinit var phoneNumber: TextView
    private lateinit var ageText: TextView
    private lateinit var viewHistory: TextView

    private var patient: Patient? = null
    private val drugList =
        listOf("Paracetamol", "Ibuprofen", "Aspirin", "Amoxicillin", "Cetirizine", "Lorazepam")

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

        // Initialize the views using the inflated view
        searchEditText = view.findViewById(R.id.search_edittext)
        durationRadioGroup = view.findViewById(R.id.radio_group_duration)
        generateButton = view.findViewById(R.id.button4)
        drugName = view.findViewById(R.id.drug_name)
        remarks = view.findViewById(R.id.remark_feild)
        patientName = view.findViewById(R.id.patient_name)
        phoneNumber = view.findViewById(R.id.phone_number_text)
        ageText = view.findViewById(R.id.age_text)
        viewHistory = view.findViewById(R.id.view_history)


        if (patient != null)
            patientName.text = patient?.firstName
        else patientName.text = "No Patient Selected"
        // Set up the ArrayAdapter for the drug list
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, drugList)
        searchEditText.setAdapter(adapter)
        searchEditText.setOnItemClickListener { parent, view, position, id ->
            val selectedDrug = parent.getItemAtPosition(position) as String
            drugName.text =
                selectedDrug + " - 500mg" // Set the selected drug name into the TextView
        }
        viewHistory.setOnClickListener{
            val intent = Intent(requireContext(), MedicalHistoryActivity::class.java)
            startActivity(intent)
        }
        // Set up the button click listener
        generateButton.setOnClickListener {
            val selectedDurationId = durationRadioGroup.checkedRadioButtonId
            if (selectedDurationId != -1) {
                val selectedRadioButton: RadioButton = view.findViewById(selectedDurationId)
                val selectedDuration = selectedRadioButton.text.toString()

                // Handle the selected duration and drug search
                val drugName = searchEditText.text.toString()
                val remark = remarks.text.toString()
                if (drugName.isNotEmpty()) {
                    val intent = Intent(requireContext(), PdfActivity::class.java)
                    intent.putExtra("drug_name", drugName)
                    intent.putExtra("duration", selectedDuration)
                    intent.putExtra("remark", remark)

                    // Start PdfActivity with the intent
                    startActivity(intent)
                }
            }
        }
        return view
    }
}
