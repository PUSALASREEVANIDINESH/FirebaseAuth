package com.example.firebaseauthactivity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null
    lateinit var txtFirstname: TextView
    lateinit var txtLastName: TextView
    lateinit var txtUsername: TextView
    lateinit var btnLogout: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")
        txtFirstname=findViewById(R.id.txtFirstName)
        txtLastName=findViewById(R.id.txtLastName)
        txtUsername=findViewById(R.id.txtEmail)
        btnLogout = findViewById(R.id.btnLogout)
        loadProfile()
    }
    private fun loadProfile(){
        val user = auth.currentUser
        val userreference=databaseReference?.child(user?.uid!!)
        txtUsername.text="Email: "+user?.email
        userreference?.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                txtFirstname.text="First Name: "+snapshot.child("firstname").value.toString()
                txtLastName.text="Last Name: "+snapshot.child("lastname").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        btnLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this@ProfileActivity,LoginActivity::class.java))
            finish()
        }
    }
}