package com.example.election

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_candidate.*
import kotlinx.android.synthetic.main.fragment_voters.*

var election = ""
class Candidate : Fragment() {
    companion object Factory {
        fun create(): Candidate = Candidate()
    }

    var candidate_nic: String? = null
    var candidate_name: String? = null
    var district_name: String? = null
    var party_name: String? = null
    var candidate_number = 0
    var election_name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_candidate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = FirebaseFirestore.getInstance();

        val candidate = Candidate.create()

        val DocRef = db.collection("se_district")
        val districts: MutableList<String?> = ArrayList()
        val adapter = ArrayAdapter(
            (activity!!),
            android.R.layout.simple_spinner_item,
            districts
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        txtcandidateDistrict.adapter = adapter
        DocRef.get().addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
            if (task.isSuccessful) {
                for (document in task.result!!) {
                    val subject = document.getString("district_name")
                    districts.add(subject)
                }
                adapter.notifyDataSetChanged()
            }
        })

        val DocP = db.collection("se_party")
        val party: MutableList<String?> = ArrayList()
        val partyadapter = ArrayAdapter(
            (activity!!),
            android.R.layout.simple_spinner_item,
            party
        )
        partyadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        txtParty.adapter = partyadapter
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
                candidate.election_name=(document.getString("election_type")+" "+document.getLong("election_year").toString())

                }
            }
            .addOnFailureListener { exception ->

            }

        btnInsertCandidate.setOnClickListener {
            val builder = AlertDialog.Builder(activity!!)
            builder.setMessage("Are you sure you want to insert this record?")
                // if the dialog is cancelable
                .setCancelable(false)
                .setPositiveButton("Yes", DialogInterface.OnClickListener {
                        dialog, id ->
                    candidate.candidate_name = txtCandidateName.text.toString()
                    candidate.candidate_nic=txtCandidateNic.text.toString()
                    candidate.district_name=txtcandidateDistrict.selectedItem.toString()
                    candidate.party_name=txtParty.selectedItem.toString()
                    candidate.candidate_number=txtCandidateNumber.selectedItem.toString().toInt()

                    val candidate = hashMapOf(
                        "candidate_nic" to  candidate.candidate_nic,
                        "candidate_name" to candidate.candidate_name,
                        "district_name" to candidate.district_name,
                        "party_name" to candidate.party_name,
                        "candidate_number" to candidate.candidate_number,
                        "election_name" to  candidate.election_name

                    )
                    db.collection("se_candidate").document(txtCandidateNic.text.toString())
                        .get()
                        .addOnSuccessListener { document ->

                            if (document.getString("candidate_nic") != txtCandidateNic.text.toString()) {

                                db.collection("se_candidate").document(txtCandidateNic.text.toString())
                                    .set(candidate)
                                    .addOnSuccessListener { documentReference ->
                                        Toast.makeText(
                                            activity!!,
                                            "Candidate Inserted successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(
                                            activity!!,
                                            "Candidate didn't get inserted",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                            }else{
                                Toast.makeText(
                                    activity!!,
                                    "Candidate has already has signed up",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    dialog.dismiss()

                })
                .setNegativeButton("No", DialogInterface.OnClickListener {
                        dialog, id ->
                    dialog.dismiss()
                })
            val alert = builder.create()
            alert.setTitle("Warning")
            alert.show()

        }

        BtnSearchcandidate.setOnClickListener {
            val docRef = db.collection("se_candidate").document(SearchCandidate.text.toString())
            docRef.get()
                .addOnSuccessListener { document ->
                    txtCandidateNic.setText(document.getString("candidate_nic"))
                    txtCandidateName.setText(document.getString("candidate_name"))
                    txtcandidateDistrict.setSelection(
                        (txtcandidateDistrict.getAdapter() as ArrayAdapter<String?>).getPosition(
                            document.getString("district_name")
                        )
                    )
                    txtParty.setSelection(
                        (txtParty.getAdapter() as ArrayAdapter<String?>).getPosition(
                            document.getString("party_name")
                        )
                    )
                    txtCandidateNumber.setSelection(
                        (txtCandidateNumber.getAdapter() as ArrayAdapter<String?>).getPosition(
                            document.getLong("candidate_number").toString()
                        )
                    )

                }
                .addOnFailureListener { exception ->
                }
        }

        btnUpdateCandidate.setOnClickListener {
            val builder = AlertDialog.Builder(activity!!)
            builder.setMessage("Are you sure you want to update this record?")
                // if the dialog is cancelable
                .setCancelable(false)
                .setPositiveButton("Yes", DialogInterface.OnClickListener {
                        dialog, id ->
                    candidate.candidate_name = txtCandidateName.text.toString()
                    candidate.candidate_nic=txtCandidateNic.text.toString()
                    candidate.district_name=txtcandidateDistrict.selectedItem.toString()
                    candidate.party_name=txtParty.selectedItem.toString()
                    candidate.candidate_number=txtCandidateNumber.selectedItem.toString().toInt()


                    val candidate = hashMapOf(
                        "candidate_nic" to  candidate.candidate_nic,
                        "candidate_name" to candidate.candidate_name,
                        "district_name" to candidate.district_name,
                        "party_name" to candidate.party_name,
                        "candidate_number" to candidate.candidate_number,
                        "election_name" to  candidate.election_name

                    )

                    db.collection("se_candidate").document(txtCandidateNic.text.toString())
                        .set(candidate)
                        .addOnSuccessListener { documentReference ->
                            Toast.makeText(activity!!, "candidate successfully updated", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(activity!!, "This candidate didn't get updated", Toast.LENGTH_SHORT).show()
                        }
                    dialog.dismiss()

                })
                .setNegativeButton("No", DialogInterface.OnClickListener {
                        dialog, id ->
                    dialog.dismiss()
                })
            val alert = builder.create()
            alert.setTitle("Warning")
            alert.show()
        }

        btnDeleteCandidate.setOnClickListener{
            val builder = AlertDialog.Builder(activity!!)
            builder.setMessage("Are you sure you want to delete this record?")
                // if the dialog is cancelable
                .setCancelable(false)
                .setPositiveButton("Yes", DialogInterface.OnClickListener {
                        dialog, id ->

                    db.collection("se_candidate").document(txtCandidateNic.text.toString())
                        .delete()
                        .addOnSuccessListener { documentReference ->
                            Toast.makeText(activity!!, "candidate successfully deleted", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(activity!!, "This candidate didn't get deleted", Toast.LENGTH_SHORT).show()
                        }
                    dialog.dismiss()

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