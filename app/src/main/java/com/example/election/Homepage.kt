package com.example.election

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_homepage.*
import kotlinx.android.synthetic.main.districtlist.*
import kotlinx.android.synthetic.main.fragment_results.*
import java.util.HashMap



class Homepage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        user_nic = intent.getStringExtra("user_nic").toString()
        user_role = intent.getStringExtra("user_role").toString()
        user_age = intent.getLongExtra("user_age",0).toInt()
        user_name = intent.getStringExtra("user_name").toString()
        user_mobile = intent.getStringExtra("user_mobile").toString()






        MainNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    publicnavhost.findNavController().navigate(R.id.nav_home)
                    true
                }
                R.id.nav_results -> {
                    publicnavhost.findNavController().navigate(R.id.mnu_results)
                    true
                }

                R.id.nav_prediction -> {
                    publicnavhost.findNavController().navigate(R.id.mnu_prediction)
                    true
                }
                R.id.nav_settings -> {
                    publicnavhost.findNavController().navigate(R.id.mnu_settings)
                    true
                }
                else -> false
            }
        }
    }



}



