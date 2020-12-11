package com.example.election

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_final_voting.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_candidate.*
import kotlinx.android.synthetic.main.fragment_voters.*

var district_name = ""
var voter_nic = ""
class FinalVoting : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final_voting)

        val db = FirebaseFirestore.getInstance();
        val vote = Vote.create()
        val DocP = db.collection("se_party")
        val party: MutableList<String?> = ArrayList()
        val partyadapter = ArrayAdapter(
            (this),
            android.R.layout.simple_spinner_item,
            party
        )
        partyadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        voteParty.adapter = partyadapter
        DocP.get().addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
            if (task.isSuccessful) {
                for (document in task.result!!) {
                    val subject = document.getString("party_name")
                    party.add(subject)
                }
                partyadapter.notifyDataSetChanged()
            }
        })

        db.collection("se_election")
            .whereEqualTo("election_is_active", "Active")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    vote.elecion_is_active=(document.getString("election_type")+" "+document.getLong("election_year").toString())

                }
            }
            .addOnFailureListener { exception ->

            }

        btnFinalvote.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Are you sure you want to vote to the Canidate:"+" "+voteCandidateNumber.selectedItem.toString()+" "+"of party:"+" "+voteParty.selectedItem.toString())
                // if the dialog is cancelable
                .setCancelable(false)
                .setPositiveButton("Yes", DialogInterface.OnClickListener {
                        dialog, id ->

                    vote.party_name = voteParty.selectedItem.toString()
                    vote.candidate_number = voteCandidateNumber.selectedItem.toString().toInt()
                    district_name=intent.getStringExtra("district_name").toString()
                    voter_nic=intent.getStringExtra("voter_nic").toString()

                    val voted = hashMapOf(
                        "voter_nic" to  voter_nic,
                        "voter_voted" to "yes",
                        "election_name" to vote.elecion_is_active
                    )

                    val vote = hashMapOf(
                        "party_name" to  vote.party_name,
                        "candidate_number" to vote.candidate_number,
                        "district_name" to district_name,
                        "election_name" to vote.elecion_is_active
                    )



                    db.collection("se_vote").document()
                        .set(vote)
                        .addOnSuccessListener { documentReference ->
                            Toast.makeText(
                                this,
                                "Voted successfully",
                                Toast.LENGTH_SHORT
                            ).show()

                            db.collection("se_voter_voted").document(voter_nic)
                                .set(voted)
                                .addOnSuccessListener { documentReference ->
                                }
                                .addOnFailureListener { e ->

                                }



                            val vote = Intent(this, VotingActivity::class.java)
                            startActivity(vote)
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                this,
                                "voting failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

        })
        .setNegativeButton("No", DialogInterface.OnClickListener {
                dialog, id ->
            dialog.dismiss()
        })
        val alert = builder.create()
        alert.setTitle("Warning")
        alert.show()


        }
    }
}