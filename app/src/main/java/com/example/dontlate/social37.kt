package com.example.dontlate

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class social37 : AppCompatActivity() {

    private lateinit var nextBtn: Button
    private lateinit var backBtn: ImageButton
    private lateinit var dltBtn: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.social_layout37)


        nextBtn = findViewById(R.id.s37_next_btn)
        backBtn = findViewById(R.id.s37_back_btn)
        dltBtn = findViewById(R.id.s37_dlt_btn)

        backBtn.setOnClickListener(BackClickListener())
        dltBtn.setOnClickListener(DltClickListener())
        nextBtn.setOnClickListener(NextClickListener())
    }

    
    internal inner class NextClickListener : View.OnClickListener {
        override fun onClick(v: View) {
            // nextBtn Click 이벤트 처리 구간
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }

    internal inner class BackClickListener : View.OnClickListener {
        override fun onClick(v: View) {
            // backBtn Click 이벤트 처리 구간
            val intent = Intent(applicationContext, social36::class.java)
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