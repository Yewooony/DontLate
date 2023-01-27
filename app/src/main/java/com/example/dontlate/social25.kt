package com.example.dontlate

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class social25 : AppCompatActivity() {

    private lateinit var nextBtn: Button
    private lateinit var backBtn: ImageButton
    private lateinit var dltBtn: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.social_layout25)

        nextBtn = findViewById(R.id.s25_next_btn)
        backBtn = findViewById(R.id.s25_back_btn)
        dltBtn = findViewById(R.id.s25_dlt_btn)

        backBtn.setOnClickListener(BackClickListener())
        dltBtn.setOnClickListener(DltClickListener())
        nextBtn.setOnClickListener(NextClickListener())
    }


    internal inner class NextClickListener : View.OnClickListener {
        override fun onClick(v: View) {
            // nextBtn Click 이벤트 처리 구간
            val intent = Intent(applicationContext, social27::class.java)
            startActivity(intent)
        }
    }

    internal inner class BackClickListener : View.OnClickListener {
        override fun onClick(v: View) {
            // backBtn Click 이벤트 처리 구간
            val intent = Intent(applicationContext, social17::class.java)
            startActivity(intent)
        }
    }

    internal inner class DltClickListener : View.OnClickListener {
        override fun onClick(v: View) {
            // dltBtn Click 이벤트 처리 구간
            finish()
        }
    }
}