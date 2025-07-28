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
        val drugList = listOf(
            Drug(
                1,
                1,
                "Analgesic",
                "Paracetamol",
                "Pain reliever and fever reducer",
                "Analgesic",
                "Tablet"
            ),
            Drug(
                2,
                2,
                "Antibiotic",
                "Amoxicillin",
                "Used for bacterial infections",
                "Antibiotic",
                "Capsule"
            ),
            Drug(3, 3, "NSAID", "Ibuprofen", "Anti-inflammatory drug", "NSAID", "Tablet")
        )
        val drugNames = drugListFull.map { it.name }
        adapter = DrugAdapter(drugList)
        recyclerView.adapter = adapter
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, drugNames)

        val autoCompleteTextView = view.findViewById<AutoCompleteTextView>(R.id.drug_auto_complete)
        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.threshold = 1

        autoCompleteTextView.setOnItemClickListener { parent, _, position, _ ->
            val selectedName = parent.getItemAtPosition(position) as String
            val selectedDrug = drugList.find { it.name == selectedName }

            selectedDrug?.let { drug ->
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Drug Details")

                val message = """
            Name: ${drug.name}
            Type: ${drug.type}
            Form: ${drug.form}
            Description: ${drug.description}
        """.trimIndent()

                builder.setMessage(message)

                builder.setPositiveButton("Add") { dialog, _ ->
                    // Add the drug to prescription or list
                    Toast.makeText(requireContext(), "${drug.name} added", Toast.LENGTH_SHORT)
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