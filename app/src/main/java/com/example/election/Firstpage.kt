package com.example.election

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.firstpage.*

class Firstpage: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.firstpage)

        lateinit var mHandler: Handler
        lateinit var mRunnable: Runnable

        mRunnable = Runnable {
            startActivity(Intent(this, Login::class.java))
            finish()
        }

        mHandler = Handler()

        //setting timer for the starting logo to get disappeared and to make the login page active
        mHandler.postDelayed(mRunnable, 3000)


        StartLogo.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
    }


