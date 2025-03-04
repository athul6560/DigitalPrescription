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
import com.zeezaglobal.digitalprescription.Activities.PdfActivity

import com.zeezaglobal.digitalprescription.R

class PrescriptionFragment : Fragment() {
    private lateinit var searchEditText: AutoCompleteTextView
    private lateinit var durationRadioGroup: RadioGroup
    private lateinit var generateButton: Button
    private lateinit var drugName: TextView
    private lateinit var remarks: EditText

    private val drugList = listOf("Paracetamol", "Ibuprofen", "Aspirin", "Amoxicillin", "Cetirizine", "Lorazepam")

    companion object {
        fun newInstance() = PrescriptionFragment()
    }

    private val viewModel: PrescriptionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        // Set up the ArrayAdapter for the drug list
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, drugList)
        searchEditText.setAdapter(adapter)
        searchEditText.setOnItemClickListener { parent, view, position, id ->
            val selectedDrug = parent.getItemAtPosition(position) as String
            drugName.text = selectedDrug+" - 500mg" // Set the selected drug name into the TextView
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
