package com.example.election

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.homemain.*



class homemain : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.homemain, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = FirebaseFirestore.getInstance();
        db.collection("se_vote").get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                  var totalvote= task.result!!.size()

                    db.collection("se_vote").orderBy("party_name", Query.Direction.DESCENDING).get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {

                            }
                        }

                    fun countOccurrences(s: String, ch: Char): Int {
                        return s.filter { it == ch }.count()
                    }

                    fun main() {
                        val s = "Eeny, meeny, miny, moe"
                        Toast.makeText(activity!!, (countOccurrences(s, 'e')).toString(), Toast.LENGTH_SHORT).show()
                    }


                    var party1 = 2.00
                    party1=(party1/totalvote.toDouble())*100

                    val handler = Handler()
                    Thread(Runnable {
                        if (party1 < 100) {


                            handler.post(Runnable {
                                PBparty1.setProgress(party1.toInt())

                            })
                        }
                    }).start()

                    var party2 = 1.00
                    party2=(party2/totalvote.toDouble())*100


                    Thread(Runnable {
                        if (party2 < 100) {


                            handler.post(Runnable {
                                PBparty2.setProgress(party2.toInt())

                            })
                        }
                    }).start()

                } else {

                }
            }



    }

    companion object {


    }
}