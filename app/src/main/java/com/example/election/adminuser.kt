package com.example.election

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_adminuser.*
import kotlinx.android.synthetic.main.fragment_locations.*
import kotlinx.android.synthetic.main.fragment_voters.*
import kotlinx.android.synthetic.main.register.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [adminuser.newInstance] factory method to
 * create an instance of this fragment.
 */
class adminuser : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adminuser, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = FirebaseFirestore.getInstance();
        val User = User.create()
        User.user_nic = txtAdminNIC.text.toString()
        User.user_name = txtAdminName.text.toString()
        User.user_age = 0
        User.user_mobile = txtAdminMobile.text.toString()
        User.user_password = txtAdminpassword.text.toString()
        User.user_role = txtUserstype.selectedItem.toString()

        BtnInsertAdmin.setOnClickListener {

            val builder = AlertDialog.Builder(activity!!)
            builder.setMessage("Are you sure you want to insert this record?")
                // if the dialog is cancelable
                .setCancelable(false)
                .setPositiveButton("Yes", DialogInterface.OnClickListener {
                        dialog, id ->

                    User.user_nic = txtAdminNIC.text.toString()
                    User.user_name = txtAdminName.text.toString()
                    User.user_age = 0
                    User.user_mobile = txtAdminMobile.text.toString()
                    User.user_password = txtAdminpassword.text.toString()
                    User.user_role = txtUserstype.selectedItem.toString()

            val user = hashMapOf(
                "user_nic" to User.user_nic,
                "user_name" to User.user_name,
                "user_age" to User.user_age,
                "user_mobile" to User.user_mobile,
                "user_password" to User.user_password,
                "user_role" to User.user_role
            )


            db.collection("se_user").document(txtAdminNIC.text.toString())
                .get()
                .addOnSuccessListener { document ->

                    if (document.getString("user_nic") != txtAdminNIC.text.toString()) {
                        db.collection("se_user").document(txtAdminNIC.text.toString())
                            .set(user)
                            .addOnSuccessListener { documentReference ->
                                Toast.makeText(
                                    activity!!,
                                    "User added successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(
                                    activity!!,
                                    "User didn't get added",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    } else {
                        Toast.makeText(
                            activity!!,
                            "User has already has signed up",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                .addOnFailureListener { exception ->

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
        BtnSearchAdmin.setOnClickListener {
            val docRef = db.collection("se_user").document(SearchAdmin.text.toString())
            docRef.get()
                .addOnSuccessListener { document ->
                    txtAdminNIC.setText(document.getString("user_nic"))
                    txtAdminName.setText(document.getString("user_name"))
                    txtAdminMobile.setText(document.getString("user_mobile"))
                    txtAdminpassword.setText(document.getString("user_password"))
                    txtUserstype.setSelection(
                        (txtUserstype.getAdapter() as ArrayAdapter<String?>).getPosition(
                            document.getString("user_role")
                        )
                    )

                }
                .addOnFailureListener { exception ->
                }
        }
        btnUpdateAdmin.setOnClickListener {
            val builder = AlertDialog.Builder(activity!!)
            builder.setMessage("Are you sure you want to update this record?")
                // if the dialog is cancelable
                .setCancelable(false)
                .setPositiveButton("Yes", DialogInterface.OnClickListener {
                        dialog, id ->

                    User.user_nic = txtAdminNIC.text.toString()
                    User.user_name = txtAdminName.text.toString()
                    User.user_age = 0
                    User.user_mobile = txtAdminMobile.text.toString()
                    User.user_password = txtAdminpassword.text.toString()
                    User.user_role = txtUserstype.selectedItem.toString()

                    val user = hashMapOf(
                        "user_nic" to User.user_nic,
                        "user_name" to User.user_name,
                        "user_age" to User.user_age,
                        "user_mobile" to User.user_mobile,
                        "user_password" to User.user_password,
                        "user_role" to User.user_role
                    )

                        db.collection("se_user").document(txtAdminNIC.text.toString())
                            .set(user)
                            .addOnSuccessListener { documentReference ->
                                Toast.makeText(
                                    activity!!,
                                    "User successfully updated",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(
                                    activity!!,
                                    "This User didn't get updated",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                    dialog.dismiss()

                        }

                       )
                .setNegativeButton("No", DialogInterface.OnClickListener {
                        dialog, id ->
                    dialog.dismiss()
                })
            val alert = builder.create()
            alert.setTitle("Warning")
            alert.show()



                }
        btnDeleteAdmin.setOnClickListener {
            val builder = AlertDialog.Builder(activity!!)
            builder.setMessage("Are you sure you want to delete this record?")
                // if the dialog is cancelable
                .setCancelable(false)
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->


                    db.collection("se_user").document(txtAdminNIC.text.toString())
                        .delete()
                        .addOnSuccessListener { documentReference ->
                            Toast.makeText(
                                activity!!,
                                "This User deleted successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                activity!!,
                                "This User didn't get deleted",
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

    }
}