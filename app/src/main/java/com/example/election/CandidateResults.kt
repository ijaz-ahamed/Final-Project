package com.example.election

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_candidate_results.*
import kotlinx.android.synthetic.main.activity_party_results.*

class CandidateResults : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candidate_results)

        txtCandidatedistrict.setText("District : "+ districtname)
    }
}