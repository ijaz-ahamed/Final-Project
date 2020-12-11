package com.example.election

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.fragment_prediction.*
import kotlinx.android.synthetic.main.fragment_results.*
import kotlinx.android.synthetic.main.fragment_results.List_Districts
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Prediction.newInstance] factory method to
 * create an instance of this fragment.
 */
var districtname=""
class Prediction : Fragment() {
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
        return inflater.inflate(R.layout.fragment_prediction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = FirebaseFirestore.getInstance();
        val DocRef = db.collection("se_district")
        val districts: MutableList<String?> = ArrayList()
        val adapter = ArrayAdapter(
            (activity!!),
            android.R.layout.simple_list_item_1,
            districts
        )
        List_Districts.adapter = adapter
        DocRef.get().addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
            if (task.isSuccessful) {
                for (document in task.result!!) {
                    val subject = document.getString("district_name")
                    districts.add(subject)
                }
                adapter.notifyDataSetChanged()
            }
        })
        List_Districts.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View, i: Int,
                                     id: Long) {
               // val category = arrayOf<String>("By Party","By Candidate")
               // val arrayAdapter = ArrayAdapter<String>(activity!!,android.R.layout.simple_list_item_1,category)
                //List_Districts.adapter = arrayAdapter
                val prediction_district = List_Districts.adapter.getItem(i).toString()
                val intent = Intent(activity!!, result_prediction_type::class.java)
                districtname=prediction_district
                startActivity(intent)
                true

            }

        })
    }
}