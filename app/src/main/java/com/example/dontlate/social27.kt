package com.example.dontlate

import androidx.core.app.NotificationCompat.Builder
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.dontlate.databinding.ActivityMainBinding

class social27 : AppCompatActivity() {



    private lateinit var nextBtn: Button
    private lateinit var backBtn: ImageButton

    private lateinit var t010 : Button
    private lateinit var t011 : Button
    private lateinit var t012 : Button
//    private lateinit var t013 : Button
//    private lateinit var t014 : Button
//    private lateinit var t015 : Button
//    private lateinit var t016 : Button
//    private lateinit var t017 : Button
//    private lateinit var t018 : Button
//    private lateinit var t019 : Button
//    private lateinit var t020 : Button
//    private lateinit var t021 : Button
//    private lateinit var t022 : Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.social_layout27)


        nextBtn = findViewById(R.id.s27_next_btn)
        backBtn = findViewById(R.id.s27_back_btn)
        t010 = findViewById(R.id.t010)
        t011 = findViewById(R.id.t011)
        t012 = findViewById(R.id.t012)



        backBtn.setOnClickListener {
            var intent = Intent(this, social25::class.java)
            startActivity(intent)
        }

        nextBtn.setOnClickListener {
            // 초기화면으로 돌아가기
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)

            //약속 생성 알림창 띄우기
            var builder = NotificationCompat.Builder(this@social27, "Create_Noti")
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("textTitle")
                .setContentText("textContent")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // 오레오 버전 이후에는 알림을 받을 때 채널이 필요
                val channel_id = "Create_Noti" // 알림을 받을 채널 id 설정
                val channel_name = "채널이름" // 채널 이름 설정
                val descriptionText = "설명글" // 채널 설명글 설정
                val importance = NotificationManager.IMPORTANCE_DEFAULT // 알림 우선순위 설정
                val channel = NotificationChannel(channel_id, channel_name, importance).apply {
                    description = descriptionText
                }

                // 만든 채널 정보를 시스템에 등록
                val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)

                // 알림 표시: 알림의 고유 ID(ex: 1002), 알림 결과
                notificationManager.notify(
                    1002, builder.build(
                    )
                )
            }
        }
    }

}