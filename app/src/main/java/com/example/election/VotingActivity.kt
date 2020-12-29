package com.example.election

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_voting.*

class VotingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voting)





        BtnLoginvote.setOnClickListener {

            //defining database connection
            val db = FirebaseFirestore.getInstance();

            //Creating a vote object
            val vote = Vote.create()

            val docR = db.collection("se_voter_voted").document(TxtvoteNIC.text.toString())
            docR.get()
                .addOnSuccessListener { document ->
                    //checking whether the voter has already voted
                    if (document.getString("voter_voted") == "yes") {
                        Toast.makeText(
                            this,
                            "Voting has been done using this NIC",
                            Toast.LENGTH_SHORT
                        ).show()
                        //locking the voting when duplicate vote is entered
                        val intent = Intent(this, DuplicateVoting::class.java)
                        startActivity(intent)
                    } else {
                        //checking the voter's NIC
                        val docRef = db.collection("se_voter").document(TxtvoteNIC.text.toString())
                        docRef.get()
                            .addOnSuccessListener { document ->
                                //Checking the voter's password
                                if (document.getString("voter_password") == TxtvotePassword.text.toString()) {
                                    val intent = Intent(this, FinalVoting::class.java)
                                    //putting the user details into the intent
                                    intent.putExtra("voter_nic", document.getString("voter_nic"))
                                    intent.putExtra(
                                        "district_name",
                                        document.getString("voter_district")
                                    )
                                    startActivity(intent)
                                } else {
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
                        .addOnFailureListener { exception ->
                            Toast.makeText(this, "Incorrect NIC", Toast.LENGTH_SHORT).show()
                        }


                }
        }
    }
