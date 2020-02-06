package com.example.programmer.click.ui.activity

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.programmer.click.R
import com.example.programmer.click.pojo.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.regex.Pattern


class SignUpActivity : AppCompatActivity() {
    private val REQ_CAM = 2
    private var mAuth: FirebaseAuth? = null

    companion object {
        lateinit var userModel: UserModel
        var is_login = false
    }

    private lateinit var name: String
    private lateinit var email: String
    private lateinit var phone: String
    private lateinit var password: String
    private lateinit var gender: String
    private var uri_img: Uri? = null


    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var myRef = database.getReference("users").child("user")

    private lateinit var dialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        dialog = ProgressDialog(this@SignUpActivity)
        buildireBase()
    }


    private fun buildireBase() {


        mAuth = FirebaseAuth.getInstance()
    }

    fun takeImage(view: View) {

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, REQ_CAM)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CAM && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val uri = data.data
                if (uri != null) {
                    uri_img = uri
                }
                Glide.with(this).load(uri.toString())
                    .placeholder(getDrawable(R.drawable.profile))
                    .fitCenter()
                    .into(profile)
                Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT).show()


            }
        }
    }

    private fun createAccount(email: String, password: String) {

        if (uri_img == null) {
            userModel = UserModel(
                name,
                email,
                null,
                phone,
                gender
            )
        } else {
            userModel = UserModel(
                name,
                email,
                uri_img.toString(),
                phone,
                gender
            )

        }

        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) { // Sign in success, update UI with the signed-in user's information
                    Log.d(LoginActivity.TAG, "createUserWithEmail:success")
                    val user = mAuth!!.currentUser
                    //updateUI(user)
                    myRef.child(user!!.uid).setValue(userModel).addOnSuccessListener {
                        dialog.dismiss()
                        is_login = true
                        Toast.makeText(
                            applicationContext, "Authentication success.",
                            Toast.LENGTH_SHORT

                        ).show()
                        onBackPressed()
                    }.addOnFailureListener(
                        {
                            is_login = false
                            Toast.makeText(
                                applicationContext, "fail cause." + it.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    )


                } else { // If sign in fails, display a message to the user.

                    is_login = false
                    Log.d(
                        LoginActivity.TAG,
                        "createUserWithEmail:failure",
                        task.exception
                    )
                    Toast.makeText(
                        applicationContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }
    }

    private fun checkEditedText(): Boolean {
        var s1 = false
        var s2 = false
        var s3 = false
        var s4 = false

        email = ed_email_s.text.toString().trim()
        password = ed_pass_s.text.toString().trim()
        phone = ed_phone.text.toString()
        gender = gender_spinner.selectedItem.toString()
        name = ed_name.text.toString()

        if (name.length != 0) {
            s3 = true

        } else {
            s3 = false
            ed_name.error = "write name first !"
            return false
        }
        if (Pattern.matches(Patterns.EMAIL_ADDRESS.toString(), email)) {
            s1 = true

        } else {
            s1 = false
            ed_email_s.error = "write valid email !"
            return false
        }
        if (password.length < 6) {
            ed_pass_s.error = "write valid password minmum 6 letters"
            s2 = false
        } else {
            s2 = true
        }


        if (phone.length < 11) {
            ed_phone.error = "write valid phone minmum 11 number"
            s4 = false
        } else {
            s4 = true
        }

        return s1 && s2 && s3 && s4


    }

    fun signup(view: View) {
        if (checkEditedText()) {
            dialog.setMessage("wait please !")
            dialog.show()
            createAccount(email, password)
        }

    }
}
