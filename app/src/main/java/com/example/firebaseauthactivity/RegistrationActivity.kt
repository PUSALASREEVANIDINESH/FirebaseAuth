package com.example.firebaseauthactivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text

class RegistrationActivity : AppCompatActivity() {
    lateinit var btnRegister: Button
    lateinit var etFirstName: EditText
    lateinit var etLastName: EditText
    lateinit var etUserName: EditText
    lateinit var etPassword: EditText

    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")
        etFirstName = findViewById(R.id.etFirstName)
        etLastName = findViewById(R.id.etLastName)
        etUserName = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnRegister = findViewById(R.id.btnRegister)
        register()
    }
    private fun register(){

        btnRegister.setOnClickListener {
            if(TextUtils.isEmpty(etFirstName.text.toString())){
                etFirstName.setError("Please Enter first Name")
                return@setOnClickListener
            }else if(TextUtils.isEmpty(etLastName.text.toString())){
                etLastName.setError("Please Enter last Name")
                return@setOnClickListener
            }else if(TextUtils.isEmpty(etUserName.text.toString())){
                etUserName.setError("Please Enter User Name")
                return@setOnClickListener
            }else if(TextUtils.isEmpty(etPassword.text.toString())){
                etPassword.setError("Please Enter password")
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(etUserName.text.toString(),etPassword.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        val currentUser = auth.currentUser
                        val currentUserDb = databaseReference?.child(currentUser?.uid!!)
                        currentUserDb?.child("firstName")?.setValue(etFirstName.text.toString())
                        currentUserDb?.child("lastName")?.setValue(etLastName.text.toString())
                        Toast.makeText(this@RegistrationActivity,"Registration Successful",Toast.LENGTH_LONG).show()
                        finish()
                    }else{
                        Toast.makeText(this@RegistrationActivity,"Registration Failed, Please try again later",Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}