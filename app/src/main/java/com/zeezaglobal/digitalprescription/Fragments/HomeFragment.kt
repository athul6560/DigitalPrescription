package com.zeezaglobal.digitalprescription.Fragments


import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.zeezaglobal.digitalprescription.Activities.DashboardActivity
import com.zeezaglobal.digitalprescription.Activities.QRScannerActivity
import com.zeezaglobal.digitalprescription.Activities.PaymentActivity
import com.zeezaglobal.digitalprescription.Adapter.PatientAdapter
import com.zeezaglobal.digitalprescription.DTO.DoctorId
import com.zeezaglobal.digitalprescription.Entity.Doctor
import com.zeezaglobal.digitalprescription.Entity.Patient
import com.zeezaglobal.digitalprescription.R
import com.zeezaglobal.digitalprescription.SharedPreference.UserId
import com.zeezaglobal.digitalprescription.Utils.Constants

import com.zeezaglobal.digitalprescription.Utils.DateUtils
import com.zeezaglobal.digitalprescription.Utils.SharedPreferencesHelper
import java.util.Calendar

class HomeFragment : Fragment(), PatientAdapter.OnPatientClickListener {

    companion object {
        fun newInstance() = HomeFragment()
    }


    private val viewModel: HomeViewModel by viewModels()

    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    private lateinit var adapter: PatientAdapter
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val doctorNameTextView: TextView = view.findViewById(R.id.name)
        val specialisationTextView: TextView = view.findViewById(R.id.specialisation)
        val dateTextView: TextView = view.findViewById(R.id.date_textview)
        val monthTextView: TextView = view.findViewById(R.id.month_textview)
        val searchView: EditText = view.findViewById(R.id.searchView)
        val searchIcon: ImageView = view.findViewById(R.id.search_icon)
        val addPatientTextView: ConstraintLayout = view.findViewById(R.id.add_new_patient)
        dateTextView.setText(DateUtils.getCurrentDate())
        monthTextView.setText(DateUtils.getCurrentMonth())
        // Retrieve token and doctorId from SharedPreferences or arguments
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        adapter = PatientAdapter(mutableListOf(), this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter



        searchIcon.setOnClickListener {
            if (searchView.text.isNullOrEmpty()) {
                // For Fragment:

                val intent = Intent(requireContext(), QRScannerActivity::class.java)
                startActivityForResult(
                    intent,
                    0
                ) // You can replace 0 with any requestCode you prefer

            } else {

                getSearchDataToRecyclercview(searchView.text.toString())
            }
        }
        searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // You can implement actions before text is changed
            }

            override fun onTextChanged(
                charSequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                // Check if the EditText is not empty and change the icon accordingly
                if (charSequence.isNullOrEmpty()) {
                    // Set the default icon (e.g., a search icon)
                    searchIcon.setImageResource(R.drawable.baseline_qr_code_24)
                } else {
                    // Set the icon for typing (e.g., a close icon)
                    searchIcon.setImageResource(R.drawable.baseline_search_24)
                }
            }

            override fun afterTextChanged(editable: Editable?) {
                // Implement any actions you want after the text is changed
            }
        })
        viewModel.patients.observe(requireActivity()) { patients ->
            adapter.addPatients(patients)
        }

        viewModel.isLoading.observe(requireActivity()) { loading ->
            isLoading = loading
        }
        //
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if (!isLoading && lastVisibleItemPosition == totalItemCount - 1) {
                    viewModel.loadPatients( UserId.getId().toLong())
                }
            }
        })

        viewModel.loadPatients( UserId.getId().toLong()) // Initial load




        val doctorId = DoctorId(UserId.getId().toLong())// Replace with actual doctor ID, possibly passed as an argument
        addPatientTextView.setOnClickListener {
            startActivity(Intent(requireContext(), PaymentActivity::class.java))
            // showCustomPopup()
        }

        viewModel.getDoctor( doctorId)
            .observe(viewLifecycleOwner, Observer { doctorResponse ->
                if (doctorResponse != null) {
                    doctorNameTextView.text =
                        "Dr. ${doctorResponse.firstName} ${doctorResponse.lastName}"
                    specialisationTextView.text = doctorResponse.specialization
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Failed to fetch doctor details",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

    }

    private fun getSearchDataToRecyclercview(text: String) {
        viewModel.searchPatient( text)
        viewModel.patientsSearch.observe(viewLifecycleOwner, Observer { patients ->

            adapter.updatePatients(patients)
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == RESULT_OK) {
            val qrCodeResult = data?.getStringExtra("SCAN_RESULT")
            Toast.makeText(
                requireContext(),
                qrCodeResult,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun showCustomPopup() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // Remove default title bar
        dialog.setContentView(R.layout.patient_custom_popup)

// Make the dialog full-screen
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))

        // Find views in the custom layout
        val title: TextView = dialog.findViewById(R.id.popup_title)


        val saveButton: Button = dialog.findViewById(R.id.save_button)
        val closeButton: Button = dialog.findViewById(R.id.close_btn)
        val firstName: EditText = dialog.findViewById(R.id.first_name)
        val dateOfBirth: EditText = dialog.findViewById(R.id.date_of_birth)
        val radioGroup: RadioGroup = dialog.findViewById(R.id.radioGroup)
        val radioMale: RadioButton = dialog.findViewById(R.id.radioMale)
        val radioFemale: RadioButton = dialog.findViewById(R.id.radioFemale)
        val contactNumber: EditText = dialog.findViewById(R.id.contact_number)
        val email: EditText = dialog.findViewById(R.id.email)
        val address: EditText = dialog.findViewById(R.id.address)
        val medicalHistory: EditText = dialog.findViewById(R.id.medical_history)
        var gender = ""
        dateOfBirth.setOnClickListener {
            showDatePicker(dateOfBirth)
        }
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioMale -> {
                    gender = "Male"
                    // Male selected
                    // Handle the male selection logic here
                }

                R.id.radioFemale -> {
                    gender = "Female"
                    // Female selected
                    // Handle the female selection logic here
                }
            }
        }
        // Close button action
        saveButton.setOnClickListener {
            val patientDetails = Patient(
                1,
                firstName = firstName.text.toString(),
                lastName = "",
                dateOfBirth = dateOfBirth.text.toString(),
                gender = gender,
                contactNumber = contactNumber.text.toString(),
                email = email.text.toString(),
                address = address.text.toString(),
                medicalHistory = medicalHistory.text.toString(),
                Doctor(sharedPreferencesHelper.getUserId())
            )


            viewModel.saveNewPatient( patientDetails)
                .observe(viewLifecycleOwner) { response ->
                    if (response != null) {
                        Toast.makeText(
                            requireContext(),
                            "Patient saved successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Failed to save patient",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            dialog.dismiss()
        }
        closeButton.setOnClickListener {
            dialog.dismiss()
        }
        // Show the dialog
        dialog.show()
    }

    private fun showDatePicker(dateOfBirth: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            dateOfBirth.context, // Use context from EditText to avoid Fragment-related issues
            { _, selectedYear, selectedMonth, selectedDay ->
                val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                dateOfBirth.setText(date) // Use setText() instead of assigning to text
            },
            year, month, day
        )

        datePickerDialog.show()
    }

    override fun onPatientClick(patient: Patient) {
        Constants.currentPatient = patient
        (activity as? DashboardActivity)?.let { dashboardActivity ->

            val viewPager = dashboardActivity.findViewById<ViewPager2>(R.id.viewPager)
            viewPager.currentItem = 1
        }
    }
}