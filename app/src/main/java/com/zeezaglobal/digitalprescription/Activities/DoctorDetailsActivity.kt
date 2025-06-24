package com.zeezaglobal.digitalprescription.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.zeezaglobal.digitalprescription.DTO.DoctorDetailsDTO
import com.zeezaglobal.digitalprescription.Dialoge.LoadingDialogue
import com.zeezaglobal.digitalprescription.R
import com.zeezaglobal.digitalprescription.SharedPreference.UserId
import com.zeezaglobal.digitalprescription.ViewModel.DoctorViewModel

class DoctorDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_doctor_deatils)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val specializations = arrayOf(
            "Cardiology", "Dermatology", "Endocrinology", "Gastroenterology",
            "Neurology", "Oncology", "Orthopedics", "Pediatrics",
            "Psychiatry", "Radiology", "Urology"
        )
        val viewmodel: DoctorViewModel by viewModels()

        val firstNameEditText = findViewById<EditText>(R.id.name_edittext)

        val specializationAutoComplete = findViewById<AutoCompleteTextView>(R.id.editTextText3)
        val registrationNumberEditText = findViewById<EditText>(R.id.editTextText4)
        val countryCodeEditText = findViewById<EditText>(R.id.editTextCountryCode)
        val phoneNumberEditText = findViewById<EditText>(R.id.editTextPhoneNumber)
        val submitButton = findViewById<Button>(R.id.button3)


        val adapter =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, specializations)
        specializationAutoComplete.setAdapter(adapter)
        submitButton.setOnClickListener {
            val loadingDialog = LoadingDialogue(this)
            loadingDialog.show("Fetching data...")


            // Collect Data
            val firstName = firstNameEditText.text.toString().trim()
            val lastName = ""
            val specialization = specializationAutoComplete.text.toString().trim()
            val registrationNumber = registrationNumberEditText.text.toString().trim()
            val countryCode = countryCodeEditText.text.toString().trim()
            val phoneNumber = phoneNumberEditText.text.toString().trim()

            // Validate Inputs
            if (firstName.isEmpty() || specialization.isEmpty() ||
                registrationNumber.isEmpty() || phoneNumber.isEmpty() || countryCode.isEmpty()
            ) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                loadingDialog.dismiss()
                return@setOnClickListener
            }



            if (!phoneNumber.matches("\\d{10}".toRegex())) {
                Toast.makeText(this, "Enter a valid 10-digit phone number", Toast.LENGTH_SHORT)
                    .show()
                loadingDialog.dismiss()
                return@setOnClickListener
            }

            // Create Doctor Data Object
            val doctorData = mapOf(
                "firstName" to firstName,
                "lastName" to lastName,
                "specialization" to specialization,
                "registrationNumber" to registrationNumber,
                "phoneNumber" to "$countryCode$phoneNumber"
            )

            val userId = UserId.getId()
            val doctorDetailsDTO = DoctorDetailsDTO(
                id = userId,  // Assuming you will provide an id or fetch it later
                firstName = doctorData["firstName"] ?: "",
                lastName = doctorData["lastName"] ?: "",
                specialization = doctorData["specialization"] ?: "",
                licenseNumber = doctorData["registrationNumber"] ?: "",
                hospitalName = "Hospital Name",  // Assuming you have a way to fetch hospital name
                contactNumber = doctorData["phoneNumber"] ?: ""
            )

            viewmodel.updateDoctor(doctorDetailsDTO).observe(
                this,
                Observer { doctorResponse ->

                    loadingDialog.dismiss()
                    startActivity(Intent(this, DashboardActivity::class.java))


                })


        }

    }
}