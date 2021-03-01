package uz.abdullajon.onlineshopping.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import kotlinx.android.synthetic.main.succesfull_add.*
import uz.abdullajon.onlineshopping.R

const val PATH_URL =
    "https://firebasestorage.googleapis.com/v0/b/onlineshopping-2d2c3.appspot.com/o/productImages%2F"

const val PREF_NAME = "sharedPreference"

class SuccessDialog(context: Context) : Dialog(context, R.style.successDialog) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.succesfull_add)
        success_close_btn.setOnClickListener { dismiss() }
        setCancelable(false)
    }
}
