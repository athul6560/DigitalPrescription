package com.zeezaglobal.digitalprescription.Fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zeezaglobal.digitalprescription.Adapter.DrugAdapter
import com.zeezaglobal.digitalprescription.Entity.Drug
import com.zeezaglobal.digitalprescription.R

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DrugAdapter
    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private var drugDropdownAdapter: ArrayAdapter<String>? = null
    private val viewModel: DrugViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)

        recyclerView = view.findViewById(R.id.drug_list_rv)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = DrugAdapter(emptyList())
        recyclerView.adapter = adapter

        autoCompleteTextView = view.findViewById(R.id.drug_auto_complete)

        // Dropdown adapter
        drugDropdownAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, ArrayList())
        autoCompleteTextView.setAdapter(drugDropdownAdapter)

        // Observe drugs from ViewModel
        viewModel.drugs.observe(viewLifecycleOwner) { drugs ->
            // Update RecyclerView if needed
            //adapter.updateList(drugs)

            // Update dropdown for autocomplete
            val names = drugs.map { it.name ?: "" }
            drugDropdownAdapter?.clear()
            drugDropdownAdapter?.addAll(names)
            drugDropdownAdapter?.notifyDataSetChanged()
        }

        viewModel.error.observe(viewLifecycleOwner) { msg ->
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        }

        // Listen to text changes for live search
        autoCompleteTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                if (query.length >= 2) { // start searching after 2 characters
                    viewModel.searchDrugs(query)
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        return view
    }
}
