package com.zeezaglobal.digitalprescription.Activities

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.stripe.android.ApiResultCallback
import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe
import com.stripe.android.model.PaymentMethod
import com.stripe.android.model.PaymentMethodCreateParams
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.stripe.android.view.CardInputWidget
import com.zeezaglobal.digitalprescription.Dialoge.BottomSheetDialog
import com.zeezaglobal.digitalprescription.R

class SubscriptionActivity : AppCompatActivity() {
    private lateinit var yearlyLayout: ConstraintLayout
    private lateinit var monthlyLayout: ConstraintLayout
    private lateinit var paymentBtn: Button
    private lateinit var stripe: Stripe
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
        yearlyLayout.setOnClickListener {
            selectPlan(yearlyLayout, monthlyLayout)
        }
        paymentBtn.setOnClickListener {
            Toast.makeText(this, "Payment button clicked", Toast.LENGTH_SHORT).show()
            val bottomSheet = BottomSheetDialog()

            // Show the bottom sheet
            bottomSheet.show(supportFragmentManager, "ModalBottomSheet")
        }
        monthlyLayout.setOnClickListener {
            selectPlan(monthlyLayout, yearlyLayout)
        }
        // Initialize Stripe SDK
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51QB0leCsOvBUMpCYLGzCsPDnPdyRI7XwsJ2fLuEBDRAQQl7LqvK3kTCT0AJwP40dKPK28Ghs7HkLtfEhBwiiNpAx00ElUqv6HL"
        )
        stripe = Stripe(this, PaymentConfiguration.getInstance(this).publishableKey)
        val cardInputWidget = findViewById<CardInputWidget>(R.id.cardInputWidget)
        val attachButton = findViewById<Button>(R.id.pay_button_sub)
        // Initialize PaymentSheet


        attachButton.setOnClickListener {
            val cardParams = cardInputWidget.paymentMethodCreateParams
            if (cardParams != null) {
                createPaymentMethod(cardParams)
            } else {
                Toast.makeText(this, "Invalid card details", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createPaymentMethod(params: PaymentMethodCreateParams) {
        stripe.createPaymentMethod(params, callback = object : ApiResultCallback<PaymentMethod> {
            override fun onSuccess(result: PaymentMethod) {
                val paymentMethodId = result.id
                if (paymentMethodId != null) {
                    attachPaymentMethodToCustomer(
                        paymentMethodId,
                        "cus_xxx123"
                    ) // Replace with actual customer ID
                }
            }

            override fun onError(e: Exception) {
                Toast.makeText(this@SubscriptionActivity, "Error: ${e.message}", Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    private fun selectPlan(selected: ConstraintLayout, other: ConstraintLayout) {
        selected.setBackgroundResource(R.drawable.selected_border)
        other.setBackgroundResource(R.drawable.edittext_border)
    }

    private fun attachPaymentMethodToCustomer(paymentMethodId: String, customerId: String) {
        Log.d("TAG", "attachPaymentMethodToCustomer: " + paymentMethodId + "-" + customerId)
        Toast.makeText(this, "" + paymentMethodId + "-" + customerId, Toast.LENGTH_SHORT).show()

    }
}

