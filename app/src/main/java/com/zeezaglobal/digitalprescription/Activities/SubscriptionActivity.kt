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
        yearlyLayout.setOnClickListener {
            selectPlan(yearlyLayout, monthlyLayout)
        }
        paymentBtn.setOnClickListener {

           // val bottomSheet = BottomSheetDialog()

            // Show the bottom sheet
           // bottomSheet.show(supportFragmentManager, "ModalBottomSheet")
            fetchPaymentIntentAndShowSheet()
        }
        monthlyLayout.setOnClickListener {
            selectPlan(monthlyLayout, yearlyLayout)
        }
        // Initialize Stripe SDK
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51QB0leCsOvBUMpCYLGzCsPDnPdyRI7XwsJ2fLuEBDRAQQl7LqvK3kTCT0AJwP40dKPK28Ghs7HkLtfEhBwiiNpAx00ElUqv6HL"
        )
        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
        stripe = Stripe(this, PaymentConfiguration.getInstance(this).publishableKey)




    }

    private fun fetchPaymentIntentAndShowSheet() {
        val clientSecret = "pi_3R2P5NCsOvBUMpCY1rCFGzPW_secret_kuk3PM6YDQicX23wcyAyos8fL" // Replace with actual test client_secret
        val ephemeralKey = "ek_test_YWNjdF8xUUIwbGVDc092QlVNcENZLEpscHQ2ZmFheGxiOURQNnhjVkRWVGdTQ0JLS0JWSmw_00XgWnMwJh" // Replace with actual test ephemeral key
        val customerId = "cus_RwF3W5Akfcz1dP" // Replace with actual test customer ID

        val paymentSheetConfig = PaymentSheet.Configuration(
            merchantDisplayName = "Your Business Name",
            customer = PaymentSheet.CustomerConfiguration(customerId, ephemeralKey)
        )

        paymentSheet.presentWithPaymentIntent(clientSecret, paymentSheetConfig)
    }



    private fun selectPlan(selected: ConstraintLayout, other: ConstraintLayout) {
        selected.setBackgroundResource(R.drawable.selected_border)
        other.setBackgroundResource(R.drawable.edittext_border)
    }


    private fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        when (paymentSheetResult) {
            is PaymentSheetResult.Completed -> {
                Toast.makeText(this, "Payment successful!", Toast.LENGTH_SHORT).show()
            }
            is PaymentSheetResult.Canceled -> {
                Toast.makeText(this, "Payment canceled", Toast.LENGTH_SHORT).show()
            }
            is PaymentSheetResult.Failed -> {
                Toast.makeText(this, "Payment failed: ${paymentSheetResult.error.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

