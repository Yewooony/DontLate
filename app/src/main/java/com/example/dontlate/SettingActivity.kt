package com.example.dontlate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SettingActivity : AppCompatActivity() {

    lateinit var backBtn: Button
    lateinit var fontSizeBtn: Button
    lateinit var notificationText : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        backBtn = findViewById(R.id.backBtnS)
        fontSizeBtn = findViewById(R.id.fontSizeBtn)
        notificationText = findViewById(R.id.notificationText)


        backBtn.setOnClickListener{
            finish()

        }

        fontSizeBtn.setOnClickListener{
            var intent = Intent(this, FontSizeActivity::class.java)
            startActivity(intent)
            finish()
        }

        //폰트 사이즈 설정
        val font: Float = (application as textApplication).getSize()
        notificationText.textSize = font
        fontSizeBtn.textSize = font

    }
}