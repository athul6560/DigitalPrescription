package com.zeezaglobal.digitalprescription.Fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zeezaglobal.digitalprescription.Adapter.DrugAdapter
import com.zeezaglobal.digitalprescription.Entity.Drug
import com.zeezaglobal.digitalprescription.Entity.PrescribedDrug
import com.zeezaglobal.digitalprescription.R

class BottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DrugAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)

        recyclerView = view.findViewById(R.id.drug_list_rv)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val drugListFull: List<Drug> = listOf(
            Drug(1, 101, "Tablet", "Paracetamol", "Pain reliever", "Analgesic", "Oral"),
            Drug(2, 102, "Capsule", "Amoxicillin", "Antibiotic", "Antibacterial", "Oral"),
            Drug(3, 103, "Syrup", "Cetirizine", "Allergy relief", "Antihistamine", "Oral"),
            // ... more drugs
        )
        // Sample dummy data
        val prescribedDrugList = listOf(
            PrescribedDrug(
                drugName = "Paracetamol",
                form = "Tablet",
                weight = 500.0,
                dosage = "1 tablet",
                frequencyPerDay = 2,
                durationDays = 5,
                instructions = "After breakfast"
            ),
            PrescribedDrug(
                drugName = "Amoxicillin",
                form = "Capsule",
                weight = 250.0,
                dosage = "1 capsule",
                frequencyPerDay = 3,
                durationDays = 7,
                instructions = "After meal"
            ),
            PrescribedDrug(
                drugName = "Ibuprofen",
                form = "Tablet",
                weight = 400.0,
                dosage = "1 tablet",
                frequencyPerDay = 2,
                durationDays = 5,
                instructions = "After lunch"
            )
        )
        val drugNames = drugListFull.map { it.name }
        adapter = DrugAdapter(prescribedDrugList)
        recyclerView.adapter = adapter
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, drugNames)

        val autoCompleteTextView = view.findViewById<AutoCompleteTextView>(R.id.drug_auto_complete)
        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.threshold = 1

        autoCompleteTextView.setOnItemClickListener { parent, _, position, _ ->
            val selectedName = parent.getItemAtPosition(position) as String
            val selectedDrug = prescribedDrugList.find { it.drugName == selectedName }

            selectedDrug?.let { drug ->
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Drug Details")

                val message = """
            Name: ${drug.drugName}
            Type: ${drug.weight}
            Form: ${drug.form}
            Description: ${drug.instructions}
        """.trimIndent()

                builder.setMessage(message)

                builder.setPositiveButton("Add") { dialog, _ ->
                    // Add the drug to prescription or list
                    Toast.makeText(requireContext(), "${drug.drugName} added", Toast.LENGTH_SHORT)
                        .show()
                    dialog.dismiss()
                }

                builder.setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }

                builder.show()
            }
        }
        return view
    }


}