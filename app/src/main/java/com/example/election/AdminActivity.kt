package com.example.election

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_admin.*
import kotlinx.android.synthetic.main.activity_homepage.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_locations.*
import kotlinx.android.synthetic.main.fragment_resultcategory.*
import kotlinx.android.synthetic.main.fragment_results.*

var user_nic = ""
var user_role = ""
var user_age = 0
var user_name = ""
var user_mobile = ""


class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)


        user_nic = intent.getStringExtra("user_nic").toString()
        user_role = intent.getStringExtra("user_role").toString()
        user_age = intent.getLongExtra("user_age",0).toInt()
        user_name = intent.getStringExtra("user_name").toString()
        user_mobile = intent.getStringExtra("user_mobile").toString()





        AdminNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_voter -> {
                    adminnavhost.findNavController().navigate(R.id.voters)
                    true
                }
                R.id.nav_party -> {
                    adminnavhost.findNavController().navigate(R.id.party)
                    true
                }
                R.id.nav_candidate -> {
                    adminnavhost.findNavController().navigate(R.id.candidates)
                    true
                }

                R.id.nav_locations -> {
                    adminnavhost.findNavController().navigate(R.id.locations)
                    true
                }
                R.id.nav_settings -> {
                    adminnavhost.findNavController().navigate(R.id.mnu_settings)
                    true
                }
                else -> false
            }
        }




    }
}