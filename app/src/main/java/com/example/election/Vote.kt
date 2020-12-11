package com.example.election

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_voting.*
import kotlinx.android.synthetic.main.fragment_vote.*




class Vote : Fragment() {
    companion object Factory {
        fun create(): Vote = Vote()
    }
    var district_name: String? = null
    var party_name: String? = null
    var candidate_number: Int = 0
    var voter_nic: String? = null
    var voter_voted: String? = null
    var elecion_is_active: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vote, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Btnstartvote.setOnClickListener {
            val vote = Intent(activity!!, VotingActivity::class.java)
            startActivity(vote)
        }

    }


}