package com.zeezaglobal.digitalprescription.Activities

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.zeezaglobal.digitalprescription.R
import com.zeezaglobal.digitalprescription.Utils.SharedPreferencesHelper
import com.zeezaglobal.digitalprescription.ViewModel.PaymentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentActivity : AppCompatActivity() {

    private lateinit var paymentBtn: Button
    private lateinit var stripe: Stripe
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    lateinit var clientSecretValue: String
    private val viewModel: PaymentViewModel by viewModels()
    private lateinit var paymentSheet: PaymentSheet
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        paymentBtn = findViewById(R.id.payment_btn)
        sharedPreferencesHelper = SharedPreferencesHelper(this)
        viewModel.createSetupIntent()
        viewModel.clientSecret.observe(this) { secret ->
            if (secret != null) {
                clientSecretValue = secret
            }
        }
        viewModel.isButtonEnabled.observe(this, Observer { isEnabled ->
            paymentBtn.isEnabled = isEnabled
        })
        paymentBtn.setOnClickListener {

            presentPaymentSheet(clientSecretValue)

        }
        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)

        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51QB0leCsOvBUMpCYLGzCsPDnPdyRI7XwsJ2fLuEBDRAQQl7LqvK3kTCT0AJwP40dKPK28Ghs7HkLtfEhBwiiNpAx00ElUqv6HL"
        )
        //  paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
        stripe = Stripe(this, PaymentConfiguration.getInstance(this).publishableKey)


    }


    private fun presentPaymentSheet(clientSecret: String) {
        // Initialize PaymentSheet with the clientSecret and the customer
        val paymentSheetConfiguration = PaymentSheet.Configuration.Builder("Mediscript")

            .build()

        // Present PaymentSheet with the SetupIntent's client secret
        paymentSheet.presentWithPaymentIntent(clientSecret, paymentSheetConfiguration)
    }


    private fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {

        when (paymentSheetResult) {
            is PaymentSheetResult.Completed -> {
                Log.d("Stripe", "Payment successful!")
                Toast.makeText(this, "Payment successful!", Toast.LENGTH_LONG).show()

            }

            is PaymentSheetResult.Canceled -> {
                Log.d("Stripe", "Payment canceled by user.")
                Toast.makeText(this, "Payment canceled", Toast.LENGTH_SHORT).show()
            }

            is PaymentSheetResult.Failed -> {
                Log.e("Stripe", "Payment failed: ${paymentSheetResult.error.message}")
                Toast.makeText(
                    this,
                    "Payment failed: ${paymentSheetResult.error.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


}







