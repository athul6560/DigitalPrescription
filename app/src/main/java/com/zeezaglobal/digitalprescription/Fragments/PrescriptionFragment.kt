package com.zeezaglobal.digitalprescription.Fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zeezaglobal.digitalprescription.R

class PrescriptionFragment : Fragment() {

    companion object {
        fun newInstance() = PrescriptionFragment()
    }

    private val viewModel: PrescriptionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_prescription, container, false)
    }
}