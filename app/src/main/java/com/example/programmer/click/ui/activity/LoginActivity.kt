package com.example.programmer.click.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.programmer.click.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserInfo
import kotlinx.android.synthetic.main.activity_login.*
import java.util.regex.Pattern


class LoginActivity : AppCompatActivity() {

    companion object {
        val TAG_USER = "user"
        val TAG = "ezzat"
    }

    private var mAuth: FirebaseAuth? = null
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buildLogin()
    }

    private fun buildLogin() {

        mAuth = FirebaseAuth.getInstance()
    }


    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth!!.currentUser
        if (currentUser != null) {
            getCurrentUser(currentUser)
        }
    }

    fun gotoHomeActivity(id: String) {
        val intent: Intent = Intent(applicationContext, HomeActivity::class.java)
        intent.putExtra(TAG_USER, id)
        intent.action = Intent.FLAG_ACTIVITY_CLEAR_TASK.toString()

        startActivity(intent)
        finish()

    }


    private fun getCurrentUser(user: UserInfo) {

        if (user != null) {
            val name: String = user.displayName.toString()
            val email: String = user.email.toString()
            val photoUrl: Uri? = user.photoUrl
            // Check if user's email is verified
            val emailVerified: Boolean = user.isEmailVerified
            val uid: String = user.uid

            gotoHomeActivity(uid)

        }


    }


    private fun signInAccount(email: String, password: String) {
        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) { // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = mAuth!!.currentUser
                    if (user != null) {
                        getCurrentUser(user)
                    }
                } else { // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        applicationContext, " ensure your email and password ! ",
                        Toast.LENGTH_SHORT
                    ).show()
                    //updateUI(null)
                }
                // ...
            }
    }

    private fun checkEditedText(): Boolean {
        var s1 = false
        var s2 = false
        email = ed_email.text.toString().trim()
        password = ed_pass.text.toString().trim()

        if (Pattern.matches(Patterns.EMAIL_ADDRESS.toString(), email)) {
            s1 = true

        } else {
            s1 = false
            ed_email.error = "write valid email !"
            return false
        }
        if (password.length < 6) {
            ed_pass.error = "write valid password minmum 6 letters"
            s2 = false
        } else {
            s2 = true
        }
        return s1 && s2

    }

    fun login(view: View) {

        if (checkEditedText()) {
            signInAccount(email, password)
        }


    }

    fun regisiterAction(view: View) {

        val intent = Intent(applicationContext, SignUpActivity::class.java)
        startActivity(intent)
    }
}


