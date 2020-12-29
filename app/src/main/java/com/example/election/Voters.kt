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
import kotlinx.android.synthetic.main.fragment_candidate.*
import kotlinx.android.synthetic.main.fragment_voters.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Voters.newInstance] factory method to
 * create an instance of this fragment.
 */
class Voters : Fragment() {

    companion object Factory {
        fun create(): Voters = Voters()
    }

    var voter_name: String? = null
    var voter_nic: String? = null
    var voter_district: String? = null
    var voter_password: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_voters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //defining database connection
        val db = FirebaseFirestore.getInstance();

        //Creating a voter object
        val voter = Voters.create()

        //Populating the districts to the list view
        val DocRef = db.collection("se_district")
        val districts: MutableList<String?> = ArrayList()
        val adapter = ArrayAdapter(
            (activity!!),
            android.R.layout.simple_spinner_item,
            districts
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        txtvoterDistrict.adapter = adapter
        DocRef.get().addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
            if (task.isSuccessful) {
                for (document in task.result!!) {
                    val subject = document.getString("district_name")
                    districts.add(subject)
                }
                adapter.notifyDataSetChanged()
            }
        })

        //Inserting a voter to the database
        BtnInsertvoter.setOnClickListener {
            val builder = AlertDialog.Builder(activity!!)
            builder.setMessage("Are you sure you want to insert this record?")
                // if the dialog is cancelable
                .setCancelable(false)
                .setPositiveButton("Yes", DialogInterface.OnClickListener {
                        dialog, id ->
                    voter.voter_name = txtvoterName.text.toString()
                    voter.voter_nic=txtvoterNIC.text.toString()
                    voter.voter_password=txtvoterpassword.text.toString()
                    voter.voter_district=txtvoterDistrict.selectedItem.toString()

                    val voter = hashMapOf(
                    "voter_name" to  voter.voter_name,
                    "voter_nic" to voter.voter_nic,
                    "voter_password" to voter.voter_password,
                    "voter_district" to voter.voter_district

                )
                    db.collection("se_user").document(txtvoterNIC.text.toString())
                        .get()
                        .addOnSuccessListener { document ->

                            if (document.getString("user_nic") != txtvoterNIC.text.toString()) {

                                db.collection("se_voter").document(txtvoterNIC.text.toString())
                                    .set(voter)
                                    .addOnSuccessListener { documentReference ->
                                        Toast.makeText(
                                            activity!!,
                                            "Voter Inserted successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(
                                            activity!!,
                                            "Voter didn't get inserted",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                            }else{
                                Toast.makeText(
                                    activity!!,
                                    "Voter has already has signed up",
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
        //Getting the voter details from the database
        BtnSearchvoter.setOnClickListener {
            val docRef = db.collection("se_voter").document(SearchVoter.text.toString())
            docRef.get()
                .addOnSuccessListener { document ->
                    txtvoterName.setText(document.getString("voter_name"))
                    txtvoterNIC.setText(document.getString("voter_nic"))
                    txtvoterpassword.setText(document.getString("voter_password"))
                    txtvoterDistrict.setSelection(
                        (txtvoterDistrict.getAdapter() as ArrayAdapter<String?>).getPosition(
                            document.getString("voter_district")
                        )
                    )

                }
                .addOnFailureListener { exception ->
                }
        }

        //Upadting the voter details in the database
        btnUpdatevoter.setOnClickListener {
            val builder = AlertDialog.Builder(activity!!)
            builder.setMessage("Are you sure you want to update this record?")
                // if the dialog is cancelable
                .setCancelable(false)
                .setPositiveButton("Yes", DialogInterface.OnClickListener {
                        dialog, id ->
                    voter.voter_name = txtvoterName.text.toString()
                    voter.voter_nic=txtvoterNIC.text.toString()
                    voter.voter_password=txtvoterpassword.text.toString()
                    voter.voter_district=txtvoterDistrict.selectedItem.toString()


                    val voter = hashMapOf(
                        "voter_name" to  voter.voter_name,
                        "voter_nic" to voter.voter_nic,
                        "voter_password" to voter.voter_password,
                        "voter_district" to voter.voter_district

                    )

                    db.collection("se_voter").document(txtvoterNIC.text.toString())
                        .set(voter)
                        .addOnSuccessListener { documentReference ->
                            Toast.makeText(activity!!, "voter successfully updated", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(activity!!, "This voter didn't get updated", Toast.LENGTH_SHORT).show()
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

        //Deleting the voter from the database
        btnDeletevoter.setOnClickListener{
            val builder = AlertDialog.Builder(activity!!)
            builder.setMessage("Are you sure you want to delete this record?")
                // if the dialog is cancelable
                .setCancelable(false)
                .setPositiveButton("Yes", DialogInterface.OnClickListener {
                        dialog, id ->
                    db.collection("se_voter").document(txtvoterNIC.text.toString())
                        .delete()
                        .addOnSuccessListener { documentReference ->
                        }
                        .addOnFailureListener { e ->

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