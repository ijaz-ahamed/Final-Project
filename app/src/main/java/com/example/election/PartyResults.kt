package com.example.election

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_party_results.*
import kotlinx.android.synthetic.main.activity_result_prediction_type.*

class PartyResults : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_party_results)

        txtdistrict.setText("District : "+ districtname)


    }
}