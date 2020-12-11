package com.example.election

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_result_prediction_type.*

class result_prediction_type : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_prediction_type)
        BtnByparty.setOnClickListener{
            val intent = Intent(this, PartyResults::class.java)
            startActivity(intent)
            true
        }

        btnByCandidate.setOnClickListener{
            val intent = Intent(this, CandidateResults::class.java)
            startActivity(intent)
            true
        }
    }
}