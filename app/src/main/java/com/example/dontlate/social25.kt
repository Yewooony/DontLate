package com.example.dontlate

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity


class social25 : AppCompatActivity() {

    //객체 생성
    private lateinit var nextBtn: Button
    private lateinit var backBtn: ImageButton
    private lateinit var dltBtn: ImageButton
    private lateinit var datePicker: DatePicker

    //년, 월, 일 담기
    private var year : Int = datePicker.year
    private var month : Int = datePicker.month+1
    private var day : Int = datePicker.dayOfMonth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.social_layout25)

        nextBtn = findViewById(R.id.s25_next_btn)
        backBtn = findViewById(R.id.s25_back_btn)
        dltBtn = findViewById(R.id.s25_dlt_btn)

        backBtn.setOnClickListener {
            var intent = Intent(this, social17::class.java)
            startActivity(intent)
        }

        dltBtn.setOnClickListener {
            finish()
        }

        nextBtn.setOnClickListener {
            var intent = Intent(this, social27::class.java)
            startActivity(intent)
        }

    }

}