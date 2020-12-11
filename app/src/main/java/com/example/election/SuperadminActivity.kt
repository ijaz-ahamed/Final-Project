package com.example.election

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_admin.*
import kotlinx.android.synthetic.main.activity_superadmin.*

class SuperadminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_superadmin)

        user_nic = intent.getStringExtra("user_nic").toString()
        user_role = intent.getStringExtra("user_role").toString()
        user_age = intent.getLongExtra("user_age",0).toInt()
        user_name = intent.getStringExtra("user_name").toString()
        user_mobile = intent.getStringExtra("user_mobile").toString()





        Superadminnav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_vote -> {
                    superadminnavhost.findNavController().navigate(R.id.vote)
                    true
                }
                R.id.nav_users -> {
                    superadminnavhost.findNavController().navigate(R.id.adminuser)
                    true
                }
                R.id.nav_elections -> {
                    superadminnavhost.findNavController().navigate(R.id.election)
                    true
                }

                R.id.nav_settings -> {
                    superadminnavhost.findNavController().navigate(R.id.settings)
                    true
                }
                else -> false
            }
        }
    }
}