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

            val db = FirebaseFirestore.getInstance();
            val docRef = db.collection("se_user").document(txtAdminNIC.text.toString())
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document.getString("user_password")==txtAdminPassword.text.toString()){
                        if(document.getString("user_role")=="admin"){
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