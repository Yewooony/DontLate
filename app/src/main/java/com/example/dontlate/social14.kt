package com.example.dontlate

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class social14 : AppCompatActivity() {

    private lateinit var nextBtn: Button
    private lateinit var backBtn: ImageButton
    private lateinit var dltBtn: ImageButton
    private lateinit var plus: ImageButton
    private lateinit var min: ImageButton
    private lateinit var numText: TextView
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.social_layout14)

        backBtn = findViewById(R.id.s14_back_btn)
        dltBtn = findViewById(R.id.s14_dlt_btn)
        nextBtn = findViewById(R.id.s14_next_btn)
        plus = findViewById(R.id.plus)
        min = findViewById(R.id.min)
        numText = findViewById(R.id.numText)

        numText.setText(count.toString() + "명")

        backBtn.setOnClickListener {
            finish()
        }

        dltBtn.setOnClickListener {
            finish()
        }


        nextBtn.setOnClickListener {
            var intent = Intent(this, social25::class.java)
            startActivity(intent)
        }

        plus.setOnClickListener {
            // Plus Click 이벤트 처리 구간
            if (count > 0 && count < 6) {
                count++
                numText.text = count.toString() + "명"
            } else {
                numText.text = count.toString() + "명"
            }
        }

        min.setOnClickListener {
            if (count > 0 && count < 6) {
                count--
                numText.text = count.toString() + "명"
            } else {
                numText.text = count.toString() + "명"
            }
        }
    }
}