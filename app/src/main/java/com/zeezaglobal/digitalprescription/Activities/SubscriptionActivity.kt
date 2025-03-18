package com.zeezaglobal.digitalprescription.Activities

import android.app.Activity
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
import com.zeezaglobal.digitalprescription.DTO.PaymentMethodPayload
import com.zeezaglobal.digitalprescription.R
import com.zeezaglobal.digitalprescription.Utils.SharedPreferencesHelper
import com.zeezaglobal.digitalprescription.ViewModel.PaymentViewModel

class SubscriptionActivity : AppCompatActivity() {
    private lateinit var yearlyLayout: ConstraintLayout
    private lateinit var monthlyLayout: ConstraintLayout
    private lateinit var paymentBtn: Button
    private lateinit var stripe: Stripe
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private var isMonthly = true
    val viewmodel: PaymentViewModel by viewModels()
    private lateinit var paymentSheet: PaymentSheet
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_subscription)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        yearlyLayout = findViewById(R.id.yearly_constrain_layout)
        monthlyLayout = findViewById(R.id.monthly_constrain_layout)
        paymentBtn = findViewById(R.id.payment_btn)
        sharedPreferencesHelper = SharedPreferencesHelper(this)
        yearlyLayout.setOnClickListener {
            selectPlan(yearlyLayout, monthlyLayout)
            isMonthly = false
        }
        monthlyLayout.setOnClickListener {
            selectPlan(monthlyLayout, yearlyLayout)
            isMonthly = true
        }
        paymentBtn.setOnClickListener {
            presentPaymentSheet("pi_3R2os7CsOvBUMpCY1iA3cV5X_secret_PwSbxbC19oV6Hfw6IWnluI3LC")

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
        val paymentSheetConfiguration = PaymentSheet.Configuration.Builder("Your Merchant Name")

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
                Toast.makeText(this, "Payment failed: ${paymentSheetResult.error.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun selectPlan(selected: ConstraintLayout, other: ConstraintLayout) {
        selected.setBackgroundResource(R.drawable.selected_border)
        other.setBackgroundResource(R.drawable.edittext_border)
    }

}







