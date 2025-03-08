package com.zeezaglobal.digitalprescription.Fragments


import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zeezaglobal.digitalprescription.Adapter.PatientAdapter
import com.zeezaglobal.digitalprescription.DTO.DoctorId
import com.zeezaglobal.digitalprescription.Entity.Doctor
import com.zeezaglobal.digitalprescription.Entity.Patient
import com.zeezaglobal.digitalprescription.R
import com.zeezaglobal.digitalprescription.Utils.DateUtils
import com.zeezaglobal.digitalprescription.Utils.SharedPreferencesHelper

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }


    private val viewModel: HomeViewModel by viewModels()
    private lateinit var token: String
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    private lateinit var adapter: PatientAdapter
    private var isLoading = false
    private  var  doctorId :Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedPreferencesHelper = SharedPreferencesHelper(requireContext())
        doctorId= sharedPreferencesHelper.getUserId()
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val doctorNameTextView: TextView = view.findViewById(R.id.name)
        val specialisationTextView: TextView = view.findViewById(R.id.specialisation)
        val dateTextView: TextView = view.findViewById(R.id.date_textview)
        val monthTextView: TextView = view.findViewById(R.id.month_textview)
        val addPatientTextView: ConstraintLayout = view.findViewById(R.id.add_new_patient)
        dateTextView.setText(DateUtils.getCurrentDate())
        monthTextView.setText(DateUtils.getCurrentMonth())
        // Retrieve token and doctorId from SharedPreferences or arguments
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        adapter = PatientAdapter(mutableListOf())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        val sharedPreferences = requireContext().getSharedPreferences("APP_PREFS", 0)
        token = sharedPreferences.getString("jwt_token", "") ?: ""

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
                    viewModel.loadPatients(token,doctorId)
                }
            }
        })

        viewModel.loadPatients(token,doctorId) // Initial load


        val id = sharedPreferences.getInt("user_id", -1).takeIf { it != -1 }?.toLong()
            ?: 0L // Default to 0L if -1

        val doctorId = DoctorId(id)// Replace with actual doctor ID, possibly passed as an argument
        addPatientTextView.setOnClickListener {
            showCustomPopup()
        }
        if (token.isNotEmpty()) {
            viewModel.getDoctor(token, doctorId)
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
        } else {
            Toast.makeText(requireContext(), "Token is missing!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showCustomPopup() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.patient_custom_popup)

        // Find views in the custom layout
        val title: TextView = dialog.findViewById(R.id.popup_title)


        val saveButton: Button = dialog.findViewById(R.id.save_button)
        val firstName: EditText = dialog.findViewById(R.id.first_name)
        val dateOfBirth: EditText = dialog.findViewById(R.id.date_of_birth)
        val gender: EditText = dialog.findViewById(R.id.gender)
        val contactNumber: EditText = dialog.findViewById(R.id.contact_number)
        val email: EditText = dialog.findViewById(R.id.email)
        val address: EditText = dialog.findViewById(R.id.address)
        val medicalHistory: EditText = dialog.findViewById(R.id.medical_history)
        // Set title text (optional)
        title.text = "This is a custom popup!"

        // Close button action
        saveButton.setOnClickListener {
            val patientDetails = Patient(
                1,
                firstName = firstName.text.toString(),
                lastName = "",
                dateOfBirth = dateOfBirth.text.toString(),
                gender = gender.text.toString(),
                contactNumber = contactNumber.text.toString(),
                email = email.text.toString(),
                address = address.text.toString(),
                medicalHistory = medicalHistory.text.toString(),
                Doctor(sharedPreferencesHelper.getUserId())
            )


            viewModel.saveNewPatient(token, patientDetails)
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

        // Show the dialog
        dialog.show()
    }
}