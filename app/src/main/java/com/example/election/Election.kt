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
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_adminuser.*
import kotlinx.android.synthetic.main.fragment_election.*
import kotlinx.android.synthetic.main.fragment_voters.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Election.newInstance] factory method to
 * create an instance of this fragment.
 */
class Election : Fragment() {
    companion object Factory {
        fun create(): Election = Election()
    }

    var election_type: String? = null
    var election_year: Int? = 0
    var election_is_active: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_election, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = FirebaseFirestore.getInstance();
        val election = Election.create()



        btnInsertElection.setOnClickListener {

            val builder = AlertDialog.Builder(activity!!)
            builder.setMessage("Are you sure you want to insert this record?")
                // if the dialog is cancelable
                .setCancelable(false)
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                    election.election_type = txtElectionName.text.toString()
                    election.election_year = txtElectionYear.text.toString().toInt()
                    election.election_is_active = txtElectionActive.selectedItem.toString()

                    val election = hashMapOf(
                        "election_type" to election.election_type,
                        "election_year" to election.election_year,
                        "election_is_active" to election.election_is_active
                    )

                    db.collection("se_election").document(
                        txtElectionName.text.toString() + " " + txtElectionYear.text.toString()
                            .toInt()
                    )
                        .get()
                        .addOnSuccessListener { document ->

                            if (document.getString("election_type") != txtElectionName.text.toString()) {
                                if (document.getString("election_year") != txtElectionYear.text.toString()) {
                                    db.collection("se_election").document(
                                        txtElectionName.text.toString() + " " + txtElectionYear.text.toString()
                                            .toInt()
                                    )
                                        .set(election)
                                        .addOnSuccessListener { documentReference ->
                                            Toast.makeText(
                                                activity!!,
                                                "Election added successfully",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        .addOnFailureListener { e ->
                                            Toast.makeText(
                                                activity!!,
                                                "Election didn't get added",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                } else {
                                    Toast.makeText(
                                        activity!!,
                                        "Election has already has added up",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }

                            } else {
                                Toast.makeText(
                                    activity!!,
                                    "Election has already has added up",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        .addOnFailureListener { exception ->

                        }
                    dialog.dismiss()

                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialog, id ->
                    dialog.dismiss()
                })
            val alert = builder.create()
            alert.setTitle("Warning")
            alert.show()
        }

        BtnSearchelection.setOnClickListener {
            val docRef = db.collection("se_election").document(SearchElection.text.toString())
            docRef.get()
                .addOnSuccessListener { document ->
                    txtElectionName.setText(document.getString("election_type"))
                    txtElectionYear.setText(document.getLong("election_year").toString())
                    txtElectionActive.setSelection(
                        (txtElectionActive.getAdapter() as ArrayAdapter<String?>).getPosition(
                            document.getString("election_is_active")
                        )
                    )

                }
                .addOnFailureListener { exception ->
                }
        }

        btnUpdateElection.setOnClickListener {

            val builder = AlertDialog.Builder(activity!!)
            builder.setMessage("Are you sure you want to update this record?")
                // if the dialog is cancelable
                .setCancelable(false)
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->

                    election.election_type = txtElectionName.text.toString()
                    election.election_year = txtElectionYear.text.toString().toInt()
                    election.election_is_active = txtElectionActive.selectedItem.toString()

                    val election = hashMapOf(
                        "election_type" to election.election_type,
                        "election_year" to election.election_year,
                        "election_is_active" to election.election_is_active
                    )

                    db.collection("se_election").document(
                        txtElectionName.text.toString() + " " + txtElectionYear.text.toString()
                            .toInt()
                    )
                        .set(election)
                        .addOnSuccessListener { documentReference ->
                            Toast.makeText(
                                activity!!,
                                "Election successfully updated",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                activity!!,
                                "Election didn't get updated",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    dialog.dismiss()

                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialog, id ->
                    dialog.dismiss()
                })
            val alert = builder.create()
            alert.setTitle("Warning")
            alert.show()
        }

        btnDeleteElection.setOnClickListener {
            val builder = AlertDialog.Builder(activity!!)
            builder.setMessage("Are you sure you want to delete this record?")
                // if the dialog is cancelable
                .setCancelable(false)
                .setPositiveButton("Yes", DialogInterface.OnClickListener {
                        dialog, id ->

                    db.collection("se_election").document(txtElectionName.text.toString() + " " + txtElectionYear.text.toString()
                        .toInt())
                        .delete()
                        .addOnSuccessListener { documentReference ->
                            Toast.makeText(
                                activity!!,
                                "Election successfully deleted",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                activity!!,
                                "Election didn't get deleted",
                                Toast.LENGTH_SHORT
                            ).show()
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


