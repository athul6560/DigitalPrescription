package com.zeezaglobal.digitalprescription.Dialoge

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import android.widget.TextView
import com.zeezaglobal.digitalprescription.R

class LoadingDialogue(private val context: Context) {
    private var dialog: Dialog? = null

    fun show(message: String = "Loading...") {
        if (dialog == null) {
            dialog = Dialog(context).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_loading, null))
                setCancelable(false)
                window?.setBackgroundDrawableResource(android.R.color.transparent)
            }
        }
        dialog?.findViewById<TextView>(R.id.tvLoadingMessage)?.text = message
        dialog?.show()
    }

    fun dismiss() {
        dialog?.dismiss()
        dialog = null
    }
}