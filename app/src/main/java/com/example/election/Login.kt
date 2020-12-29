package com.example.election

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.firstpage.*
import kotlinx.android.synthetic.main.fragment_voters.*


class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)





        Linkregister.setText(R.string.Linkregister)


        Linkregister.setOnClickListener {
                    val registernav = Intent(this, Register::class.java)
                    startActivity(registernav)
                    true
                }



        BtnLogin.setOnClickListener {

            //defining database connection
            val db = FirebaseFirestore.getInstance();

            //creating a User object
            val User = User.create()

            //checking the user NIC
            val docRef = db.collection("se_user").document(TxtNIC.text.toString())
            docRef.get()
                .addOnSuccessListener { document ->
                    //Checking the user password
                    if (document.getString("user_password")==TxtPassword.text.toString()){
                        //Checking the user role
                        if(document.getString("user_role")=="user"){
                            val intent = Intent(this, Homepage::class.java)
                            //putting the user details into the intent
                            intent.putExtra("user_nic", document.getString("user_nic"))
                            intent.putExtra("user_role", document.getString("user_role"))
                            intent.putExtra("user_age", document.getLong("user_age"))
                            intent.putExtra("user_name", document.getString("user_name"))
                            intent.putExtra("user_mobile", document.getString("user_mobile"))
                            startActivity(intent)
                        }
                        else if (document.getString("user_role")=="officer"){
                            val intent = Intent(this, AdminActivity::class.java)
                            //putting the user details into the intent
                            intent.putExtra("user_nic", document.getString("user_nic"))
                            intent.putExtra("user_role", document.getString("user_role"))
                            intent.putExtra("user_age", document.getLong("user_age"))
                            intent.putExtra("user_name", document.getString("user_name"))
                            intent.putExtra("user_mobile", document.getString("user_mobile"))
                            startActivity(intent)
                        }
                        else if (document.getString("user_role")=="admin"){
                            val intent = Intent(this, SuperadminActivity::class.java)
                            //putting the user details into the intent
                            intent.putExtra("user_nic", document.getString("user_nic"))
                            intent.putExtra("user_role", document.getString("user_role"))
                            intent.putExtra("user_age", document.getLong("user_age"))
                            intent.putExtra("user_name", document.getString("user_name"))
                            intent.putExtra("user_mobile", document.getString("user_mobile"))
                            startActivity(intent)
                        }
                    }
                    else {
                        Toast.makeText(this, "Incorrect NIC or password", Toast.LENGTH_SHORT).show()
                    }


                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Incorrect NIC", Toast.LENGTH_SHORT).show()
                }


        }















    }


}