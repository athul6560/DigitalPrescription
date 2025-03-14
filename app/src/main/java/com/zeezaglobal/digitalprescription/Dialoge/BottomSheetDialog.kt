package com.zeezaglobal.digitalprescription.Dialoge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zeezaglobal.digitalprescription.R

class BottomSheetDialog : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the bottom sheet layout
        val view = inflater.inflate(R.layout.bottom_sheet_layout, container, false)

        // Find buttons from the layout
        val payBtn: Button = view.findViewById(R.id.button_1)


        // Set click listener for Algorithm button
        payBtn.setOnClickListener {
            Toast.makeText(activity, "First Button Clicked", Toast.LENGTH_SHORT).show()
            dismiss() // Close the bottom sheet
        }



        return view
    }
}