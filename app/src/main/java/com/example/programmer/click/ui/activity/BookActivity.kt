package com.example.programmer.click.ui.activity

import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.programmer.click.R
import kotlinx.android.synthetic.main.activity_book.*


class BookActivity : AppCompatActivity() {
    private lateinit var date: String
    private lateinit var uid: String
    private lateinit var fee: String
    private lateinit var clinic_key: String
    private lateinit var doc_key: String
    private lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)

        val bundle = intent.extras
        date = bundle!!.getString("date", "")
        uid = bundle.getString("uid", "")
        fee = bundle.getString("fee", "")
        doc_key = bundle.getString("doc_key", "")
        clinic_key = bundle.getString("clinic_key", "")
        Log.d("wezz", clinic_key)


        buildAlert()
        buildPayMethoed()
    }

    private fun buildAlert() {
        var alertBuilder: AlertDialog.Builder = AlertDialog.Builder(this@BookActivity)
        alertBuilder.setTitle("Confirm before purchase")
        alertBuilder.setMessage(
            "Card number: " + card_form.cardNumber.toString() + "\n" +
                    "Card expiry date: " + card_form.expirationDateEditText.text.toString().toString() + "\n" +
                    "Card CVV: " + card_form.cvv.toString() + "\n" +
                    "Postal code: " + card_form.postalCode.toString() + "\n" +
                    "Phone number: " + card_form.mobileNumber
        )
        alertBuilder.setPositiveButton("Confirm",
            DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()
                Toast.makeText(applicationContext, "Thank you for purchase", Toast.LENGTH_LONG)
                    .show()
            })
        alertBuilder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.dismiss() })
        alertDialog = alertBuilder.create()


    }

    private fun buildPayMethoed() {

        card_form
            .cardRequired(true)
            .expirationRequired(true)
            .cvvRequired(true)
            .postalCodeRequired(true)
            .mobileNumberRequired(true)
            .mobileNumberExplanation("SMS is required on this number")
            .setup(this@BookActivity)
        card_form.cvvEditText.inputType = (InputType.TYPE_CLASS_NUMBER)


        btnBuy.setOnClickListener({
            if (card_form.isValid) {
                alertDialog.show()
            } else {
                Toast.makeText(applicationContext, "Please complete the form", Toast.LENGTH_LONG)
                    .show()
            }

        })


    }

}
