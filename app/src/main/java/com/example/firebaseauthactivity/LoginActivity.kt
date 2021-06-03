package com.example.firebaseauthactivity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var btnLogin: Button
    lateinit var etUsernameLogin: EditText
    lateinit var etPasswordLogin: EditText
    lateinit var txtRegister: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        btnLogin = findViewById(R.id.btnLogin)
        etUsernameLogin=findViewById(R.id.etUsernameLogin)
        etPasswordLogin = findViewById(R.id.etPasswordLogin)
        txtRegister = findViewById(R.id.txtRegister)
        val currentuser=auth.currentUser
        if(currentuser!=null){
            startActivity(Intent(this@LoginActivity,ProfileActivity::class.java))
            finish()
        }
        login()
    }
    private fun login(){
        btnLogin.setOnClickListener {
            if(TextUtils.isEmpty(etUsernameLogin.text.toString())){
                etUsernameLogin.setError("Please Enter User Name")
                return@setOnClickListener
            }else if(TextUtils.isEmpty(etPasswordLogin.text.toString())){
                etPasswordLogin.setError("Please Enter password")
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(etUsernameLogin.text.toString(),etPasswordLogin.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        startActivity(Intent(this@LoginActivity,ProfileActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this@LoginActivity,"Login Failed, Please try again later",
                            Toast.LENGTH_LONG).show()
                    }
                }
        }
        txtRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity,RegistrationActivity::class.java))
        }
    }
}