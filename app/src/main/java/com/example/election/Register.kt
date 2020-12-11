package com.example.election

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_voters.*

import kotlinx.android.synthetic.main.register.*

class Register: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)


        btnRegister.setOnClickListener {
            val db = FirebaseFirestore.getInstance();
            val User = User.create()



                    User.user_nic = txtRNIC.text.toString()
                    User.user_name=txtNameNIC.text.toString()
                    User.user_age= txtAge.text.toString().toInt()
                    User.user_mobile=txtMobile.text.toString()
                    User.user_password=txtPassword.text.toString()
                    User.user_role="user"

                    val voter = hashMapOf(
                        "user_nic" to  User.user_nic,
                        "user_name" to User.user_name,
                        "user_age" to User.user_age,
                        "user_mobile" to User.user_mobile,
                        "user_password" to User.user_password,
                        "user_role" to User.user_role



                    )

                     db.collection("se_user").document(txtRNIC.text.toString())
                        .get()
                         .addOnSuccessListener { document ->
                             if(txtPassword.text.toString()==txtConfirmpassword.text.toString())
                             {
                                 if (document.getString("user_nic")!=txtRNIC.text.toString()) {
                                     db.collection("se_user").document(txtRNIC.text.toString())
                                         .set(User)
                                         .addOnSuccessListener { documentReference ->
                                             val loginnav = Intent(this, Login::class.java)
                                             startActivity(loginnav)
                                             true
                                         }
                                         .addOnFailureListener { e ->

                                         }
                                 }
                                 else {
                                     Toast.makeText(this, "NIC already has signed up", Toast.LENGTH_SHORT).show()
                                 }
                             }
                             else{
                                 Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show()
                             }

                         }
                         .addOnFailureListener { exception ->

                             }






                }


    }
}