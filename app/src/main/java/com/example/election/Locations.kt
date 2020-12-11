package com.example.election

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_locations.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Locations.newInstance] factory method to
 * create an instance of this fragment.
 */
class Locations : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {




        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_locations, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = FirebaseFirestore.getInstance();

        val district = district.create()

        btnInsertDistrict.setOnClickListener {
            val builder = AlertDialog.Builder(activity!!)
            builder.setMessage("Are you sure you want to insert this record?")
                // if the dialog is cancelable
                .setCancelable(false)
                .setPositiveButton("Yes", DialogInterface.OnClickListener {
                        dialog, id ->
                    district.district_name = txtDistrictName.text.toString()
                    district.province_name=txtProvince.selectedItem.toString()

                    val district = hashMapOf(
                        "district_name" to  district.district_name,
                        "province_name" to district.province_name
                    )

                    db.collection("se_district").document(txtDistrictName.text.toString())
                        .set(district)
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
        BtnSearch.setOnClickListener {
            val docRef = db.collection("se_district").document(SearchDistrict.text.toString())
            docRef.get()
                .addOnSuccessListener { document ->
                    txtDistrictName.setText(document.getString("district_name"))
                    txtProvince.setSelection(
                        (txtProvince.getAdapter() as ArrayAdapter<String?>).getPosition(
                            document.getString("province_name")
                        )
                    )

                }
                .addOnFailureListener { exception ->
                }
        }

        btnUpdateDistrict.setOnClickListener {
            val builder = AlertDialog.Builder(activity!!)
            builder.setMessage("Are you sure you want to update this record?")
                // if the dialog is cancelable
                .setCancelable(false)
                .setPositiveButton("Yes", DialogInterface.OnClickListener {
                        dialog, id ->
            district.district_name = txtDistrictName.text.toString()
            district.province_name=txtProvince.selectedItem.toString()


            val district = hashMapOf(
                "district_name" to  district.district_name,
                "province_name" to district.province_name
            )

            db.collection("se_district").document(txtDistrictName.text.toString())
                .set(district)
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

        btnDeleteDistrict.setOnClickListener{
            val builder = AlertDialog.Builder(activity!!)
            builder.setMessage("Are you sure you want to delete this record?")
                // if the dialog is cancelable
                .setCancelable(false)
                .setPositiveButton("Yes", DialogInterface.OnClickListener {
                        dialog, id ->

            db.collection("se_district").document(txtDistrictName.text.toString())
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Locations.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Locations().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}