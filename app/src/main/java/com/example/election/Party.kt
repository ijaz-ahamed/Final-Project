package com.example.election

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_party.*
import kotlinx.android.synthetic.main.fragment_voters.*
import petrov.kristiyan.colorpicker.ColorPicker
import petrov.kristiyan.colorpicker.ColorPicker.OnChooseColorListener


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Party.newInstance] factory method to
 * create an instance of this fragment.
 */
var col = ""
var img= ""
class Party : Fragment() {

    companion object Factory {
        fun create(): Party = Party()
    }
        var party_name: String? = null
        var party_image: String? = null
        var party_color: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_party, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = FirebaseFirestore.getInstance();
        val party = Party.create()
        fun openGalleryForImage() {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 1000)
        }





        btnImage.setOnClickListener {
            openGalleryForImage()
             fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
                super.onActivityResult(requestCode, resultCode, data)
                if (resultCode == Activity.RESULT_OK && requestCode == 1000) {
                    ImgParty.setImageURI(data?.data)
                    var img= data?.data.toString()
                    party.party_color=img
                }
            }

        }
        btnColor.setOnClickListener{
            val colorPicker = ColorPicker(activity)
            colorPicker.show()
            colorPicker.setOnChooseColorListener(object : OnChooseColorListener {
                override fun onChooseColor(position: Int, color: Int) {
                    imgColor.setBackgroundColor(color)
                    val hexColor = Integer.toHexString(color).substring(2)
                    var col= ("#"+hexColor)
                    party.party_color=col
                }

                override fun onCancel() {
                    // put code
                }
            })
        }
        btnInsertParty.setOnClickListener{
            val builder = AlertDialog.Builder(activity!!)
            builder.setMessage("Are you sure you want to insert this record?")
                // if the dialog is cancelable
                .setCancelable(false)
                .setPositiveButton("Yes", DialogInterface.OnClickListener {
                        dialog, id ->
                    party.party_name = txtPartyName.text.toString()


                    val party = hashMapOf(
                        "party_name" to  party.party_name,
                        "party_image" to party.party_image,
                        "party_color" to party.party_color
                    )
                    db.collection("se_party").document(txtPartyName.text.toString())
                        .get()
                        .addOnSuccessListener { document ->

                            if (document.getString("party_name") != txtPartyName.text.toString()) {

                                db.collection("se_party").document(txtPartyName.text.toString())
                                    .set(party)
                                    .addOnSuccessListener { documentReference ->
                                        Toast.makeText(
                                            activity!!,
                                            "Party Inserted successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(
                                            activity!!,
                                            "Party didn't get inserted",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                            }else{
                                Toast.makeText(
                                    activity!!,
                                    "Party has already has added",
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
        BtnSearchParty.setOnClickListener {
            val docRef = db.collection("se_party").document(SearchParty.text.toString())
            docRef.get()
                .addOnSuccessListener { document ->
                    txtPartyName.setText(document.getString("party_name"))
                    //ImgParty.setImageURI(Uri.parse(document.getString("party_image")))
                    val myColor: Int = Color.parseColor(document.getString("party_color"))
                    imgColor.setBackgroundColor(myColor)

                }
                .addOnFailureListener { exception ->
                }
        }
        btnUpdateParty.setOnClickListener {
            val builder = AlertDialog.Builder(activity!!)
            builder.setMessage("Are you sure you want to update this record?")
                // if the dialog is cancelable
                .setCancelable(false)
                .setPositiveButton("Yes", DialogInterface.OnClickListener {
                        dialog, id ->
                    party.party_name = txtPartyName.text.toString()


                    val party = hashMapOf(
                        "party_name" to  party.party_name,
                        "party_image" to party.party_image,
                        "party_color" to party.party_color

                    )

                    db.collection("se_party").document(txtPartyName.text.toString())
                        .set(party)
                        .addOnSuccessListener { documentReference ->
                            Toast.makeText(activity!!, "party successfully updated", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(activity!!, "This party didn't get updated", Toast.LENGTH_SHORT).show()
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
        btnDeleteParty.setOnClickListener{
            val builder = AlertDialog.Builder(activity!!)
            builder.setMessage("Are you sure you want to delete this record?")
                // if the dialog is cancelable
                .setCancelable(false)
                .setPositiveButton("Yes", DialogInterface.OnClickListener {
                        dialog, id ->
                    db.collection("se_party").document(txtPartyName.text.toString())
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1000) {
            ImgParty.setImageURI(data?.data) // handle chosen image
        }
    }

}