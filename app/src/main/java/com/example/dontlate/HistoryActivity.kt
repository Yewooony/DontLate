package com.example.dontlate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class HistoryActivity : AppCompatActivity() {

    lateinit var backBtn: Button
    lateinit var emptyText : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        backBtn = findViewById(R.id.backBtnH)
        emptyText = findViewById(R.id.emptyText)

        backBtn.setOnClickListener{
            finish()
        }

        //폰트 사이즈 설정
        val font: Float = (application as textApplication).getSize()
        emptyText.textSize = font

    }
}