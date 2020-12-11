package com.example.election

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.fragment_resultcategory.*
import kotlinx.android.synthetic.main.fragment_results.*
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Results.newInstance] factory method to
 * create an instance of this fragment.
 */
class Results : Fragment() {
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
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_results, container, false)

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
        List_Districts.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View, i: Int,
                                     id: Long) {
                //val category = arrayOf<String>("By Party","By Candidate")
                //val arrayAdapter = ArrayAdapter<String>(activity!!,android.R.layout.simple_list_item_1,category)
                //List_Districts.adapter = arrayAdapter
                val result_district = List_Districts.adapter.getItem(i).toString()
                val intent = Intent(activity!!, result_prediction_type::class.java)
                districtname=result_district
                startActivity(intent)
                true

            }

        })


    }




}