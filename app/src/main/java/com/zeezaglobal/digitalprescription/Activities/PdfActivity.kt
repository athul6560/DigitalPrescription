package com.zeezaglobal.digitalprescription.Activities
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.zeezaglobal.digitalprescription.R
import com.zeezaglobal.digitalprescription.Utils.Constants
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class PdfActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pdf)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var patient = Constants.currentPatient
        val drugName = intent.getStringExtra("drug_name")
        val duration = intent.getStringExtra("duration")
        val remark = intent.getStringExtra("remark")

        val textView: TextView = findViewById(R.id.name_drug_textview)
        val shareButton: Button = findViewById(R.id.button_share)

        textView.text = "Drug: $drugName\nDuration: $duration\nRemark: $remark"

        shareButton.setOnClickListener {
            if (drugName != null && duration != null && remark != null) {
                generateAndSharePdf(drugName, duration, remark,"Dr Abinand AV","Cardiologist", getSignatureBitmap())
            }
        }
    }
    fun getSignatureBitmap(): Bitmap {
        // Create a sample Bitmap for illustration purposes
        val bitmap = Bitmap.createBitmap(200, 100, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        paint.color = Color.BLACK
        paint.textSize = 30f
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL

        // Draw a simple signature as text on the canvas (e.g., "Dr. Abinand AV")
        canvas.drawText("Dr. Abinand AV", 10f, 50f, paint)

        return bitmap
    }
    private fun generateAndSharePdf(drugName: String, duration: String, remark: String, doctorName: String, specialization: String, signatureBitmap: Bitmap) {
        val pdfDocument = PdfDocument()
        // Set up page info and canvas
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4 size
        val page = pdfDocument.startPage(pageInfo)

        val canvas: Canvas = page.canvas
        val paint = Paint()

        // Set paint properties
        paint.color = Color.BLACK
        paint.textSize = 16f

        // Add date on the left side
        val currentDateTime = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        canvas.drawText("Date: $currentDateTime", 50f, 50f, paint)

        // Add heading
        paint.textSize = 20f
        paint.color = Color.BLACK
        canvas.drawText("Digital Prescription", 200f, 50f, paint)

        // Write prescription data into PDF
        paint.textSize = 16f
        canvas.drawText("Drug: $drugName", 100f, 100f, paint)
        canvas.drawText("Duration: $duration", 100f, 150f, paint)
        canvas.drawText("Remark: $remark", 100f, 200f, paint)

        // Add doctor's details
        canvas.drawText("Doctor: $doctorName", 100f, 250f, paint)
        canvas.drawText("Specialization: $specialization", 100f, 300f, paint)

        // Add signature
        if (signatureBitmap != null) {
            canvas.drawBitmap(signatureBitmap, 100f, 350f, null)
        }

        // Add a QR code with doctor details
        val qrCodeData = "Doctor: $doctorName\nSpecialization: $specialization"
        val qrCode = generateQrCode(qrCodeData)
        if (qrCode != null) {
            canvas.drawBitmap(qrCode, 100f, 550f, null)
        }

        val tickBitmap: Bitmap? = BitmapFactory.decodeResource(resources, R.drawable.baseline_check_24)

        if (tickBitmap != null) {
            // Ensure the bitmap is loaded correctly before drawing
            canvas.drawBitmap(tickBitmap, 400f, 350f, null)
        } else {
            // Handle the error or provide a fallback
            Log.e("PDF", "Failed to load the tick bitmap")
        }

        pdfDocument.finishPage(page)

        // Generate filename with date and time
        val filename = "Prescription_$currentDateTime.pdf"

        // Save PDF to external storage
        try {
            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), filename)
            val fileOutputStream = FileOutputStream(file)
            pdfDocument.writeTo(fileOutputStream)
            pdfDocument.close()

            // Share the generated PDF
            sharePdf(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    fun generateQrCode(data: String): Bitmap? {
        try {
            val qrCodeWriter = QRCodeWriter()
            val hints = hashMapOf<EncodeHintType, Any>()
            hints[EncodeHintType.MARGIN] = 1
            val bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200, hints)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val qrCodeBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    qrCodeBitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
                }
            }
            return qrCodeBitmap
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
    private fun sharePdf(file: File) {
        // Use FileProvider to get content:// URI
        val fileUri = FileProvider.getUriForFile(
            this,
            "${applicationContext.packageName}.provider",
            file
        )

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "application/pdf"
        shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri)
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        startActivity(Intent.createChooser(shareIntent, "Share PDF"))
    }
}
