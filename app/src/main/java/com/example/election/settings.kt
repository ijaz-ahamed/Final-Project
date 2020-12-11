package com.example.election

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.core.view.marginBottom
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_voters.*
import kotlinx.android.synthetic.main.register.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [settings.newInstance] factory method to
 * create an instance of this fragment.
 */
class settings : Fragment() {
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
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    private fun chooseThemeDialog() {

        val builder = AlertDialog.Builder(activity!!)
        builder.setTitle("Choose theme")
        val styles = arrayOf("Light","Dark","System default")
        val checkedItem = 0

        builder.setSingleChoiceItems(styles, checkedItem) { dialog, which ->

            when (which) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    dialog.dismiss()
                    if(user_role=="officer") {
                        val home = Intent(activity!!, AdminActivity::class.java)
                        startActivity(home)
                    }
                    else if (user_role=="user") {
                        val home = Intent(activity!!, Homepage::class.java)
                        startActivity(home)
                    }
                    else if (user_role=="admin") {
                        val home = Intent(activity!!, SuperadminActivity::class.java)
                        startActivity(home)
                    }
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    dialog.dismiss()
                    if(user_role=="officer") {
                        val home = Intent(activity!!, AdminActivity::class.java)
                        startActivity(home)
                    }
                    else if (user_role=="user") {
                        val home = Intent(activity!!, Homepage::class.java)
                        startActivity(home)
                    }
                    else if (user_role=="admin") {
                        val home = Intent(activity!!, SuperadminActivity::class.java)
                        startActivity(home)
                    }
                }
                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    dialog.dismiss()
                    if(user_role=="officer") {
                        val home = Intent(activity!!, AdminActivity::class.java)
                        startActivity(home)
                    }
                    else if (user_role=="user") {
                        val home = Intent(activity!!, Homepage::class.java)
                        startActivity(home)
                    }
                    else if (user_role=="admin") {
                        val home = Intent(activity!!, SuperadminActivity::class.java)
                        startActivity(home)
                    }
                }

            }


        }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Settingsuser.setText(user_nic)
        TxtChangepassword.isVisible=false
        TxtConfirmpassword.isVisible=false
        btnpassword.isVisible=false
        Btnlogout.setOnClickListener {
            val builder = AlertDialog.Builder(activity!!)
            builder.setMessage("Are you sure you want to logout?")
                // if the dialog is cancelable
                .setCancelable(false)
                .setPositiveButton("Yes", DialogInterface.OnClickListener {
                        dialog, id ->
            val logout = Intent(activity!!, Login::class.java)
            startActivity(logout)
                })
                .setNegativeButton("No", DialogInterface.OnClickListener {
                        dialog, id ->
                    dialog.dismiss()
                })
            val alert = builder.create()
            alert.setTitle("Warning")
            alert.show()
        }
        Btntheme.setOnClickListener{
            chooseThemeDialog()
        }

        btnChangepassword.setOnClickListener{
            TxtChangepassword.isVisible=true
            TxtConfirmpassword.isVisible=true
            btnpassword.isVisible=true
        }

        btnpassword.setOnClickListener{

            val User = User.create()
            val db = FirebaseFirestore.getInstance();

            if(TxtChangepassword.text.toString()==TxtConfirmpassword.text.toString())
            {
            val builder = AlertDialog.Builder(activity!!)
            builder.setMessage("Are you sure you want change the password?")
                // if the dialog is cancelable
                .setCancelable(false)
                .setPositiveButton("Yes", DialogInterface.OnClickListener {
                        dialog, id ->
                    User.user_nic= Settingsuser.text.toString()
                    User.user_password=TxtChangepassword.text.toString()


                    val user = hashMapOf(
                        "user_nic" to  User.user_nic,
                        "user_password" to User.user_password,
                        "user_age" to  user_age,
                        "user_mobile" to user_mobile,
                        "user_name" to  user_name,
                        "user_role" to user_role


                    )



                    db.collection("se_user").document(Settingsuser.text.toString())
                        .set(user)
                        .addOnSuccessListener { documentReference ->
                            Toast.makeText(activity!!, "Password changed successfully", Toast.LENGTH_SHORT).show()
                            TxtChangepassword.isVisible=false
                            TxtConfirmpassword.isVisible=false
                            btnpassword.isVisible=false
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(activity!!, "Password not changed", Toast.LENGTH_SHORT).show()
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
            else {
                Toast.makeText(activity!!, "Password doesn't match", Toast.LENGTH_SHORT).show()
            }

        }
    }
}