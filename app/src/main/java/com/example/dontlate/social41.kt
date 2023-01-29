package com.example.dontlate

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class social41 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.social_layout41)

        findViewById<View>(R.id.s41_back_btn).setOnClickListener{
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.mainFrameLayout, SocialFragment())
//                .commit()
            finish()
        }
    }
}