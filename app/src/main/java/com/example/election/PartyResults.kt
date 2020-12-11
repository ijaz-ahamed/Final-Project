package com.example.election

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_party_results.*
import kotlinx.android.synthetic.main.activity_result_prediction_type.*
import kotlinx.android.synthetic.main.homemain.*

class PartyResults : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_party_results)

        txtdistrict.setText("District : "+ districtname)
        val db = FirebaseFirestore.getInstance();
        db.collection("se_vote")
            .whereEqualTo("district_name", districtname).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var totalvote= task.result!!.size()


                    var party1 = 2.00
                    party1=(party1/totalvote.toDouble())*100

                    val handler = Handler()
                    Thread(Runnable {
                        if (party1 < 100) {


                            handler.post(Runnable {
                                PBDparty1.setProgress(party1.toInt())

                            })
                        }
                    }).start()

                    var party2 = 2.00
                    party2=(party2/totalvote.toDouble())*100


                    Thread(Runnable {
                        if (party2 < 100) {


                            handler.post(Runnable {
                                PBDparty2.setProgress(party2.toInt())

                            })
                        }
                    }).start()

                } else {

                }
            }


    }
}