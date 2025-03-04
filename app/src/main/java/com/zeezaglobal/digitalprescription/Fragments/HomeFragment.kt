package com.zeezaglobal.digitalprescription.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.zeezaglobal.digitalprescription.DTO.DoctorId
import com.zeezaglobal.digitalprescription.R
import com.zeezaglobal.digitalprescription.Utils.DateUtils

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
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
        dateTextView.setText(DateUtils.getCurrentDate())
        monthTextView.setText(DateUtils.getCurrentMonth())
        // Retrieve token and doctorId from SharedPreferences or arguments
        val sharedPreferences = requireContext().getSharedPreferences("APP_PREFS", 0)
        val token = sharedPreferences.getString("jwt_token", "") ?: ""
        val id = sharedPreferences.getInt("user_id", -1).takeIf { it != -1 }?.toLong() ?: 0L // Default to 0L if -1

        val doctorId = DoctorId(id)// Replace with actual doctor ID, possibly passed as an argument

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
}