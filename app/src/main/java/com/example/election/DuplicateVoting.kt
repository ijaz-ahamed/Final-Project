package com.example.election

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_duplicate_voting.*
import kotlinx.android.synthetic.main.activity_login.*

class DuplicateVoting : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_duplicate_voting)


        btnAdminLogin.setOnClickListener {

            //defining database connection
            val db = FirebaseFirestore.getInstance();

            //checking the admin's NIC
            val docRef = db.collection("se_user").document(txtAdminNIC.text.toString())
            docRef.get()
                .addOnSuccessListener { document ->

                    //Checking the admin's password
                    if (document.getString("user_password")==txtAdminPassword.text.toString()){

                        //Checking the admin role
                        if(document.getString("user_role")=="admin"){
                            //if the admin credentials provided the voting can be resumed again
                            val intent = Intent(this, VotingActivity::class.java)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(
                                this,
                                "Incorrect Admin access",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    else{
                        Toast.makeText(
                            this,
                            "Incorrect NIC or password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Incorrect NIC", Toast.LENGTH_SHORT).show()
                }


        }
    }
}