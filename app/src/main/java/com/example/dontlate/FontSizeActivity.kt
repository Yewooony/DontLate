package com.example.dontlate

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.Dimension
import androidx.appcompat.app.AppCompatActivity


class FontSizeActivity : AppCompatActivity() {

    lateinit var backBtn: Button

    //폰트 크기 변경 - sizebar: 크기 변경, fontSizeEx: 변경한 폰트 크기 확인 텍스트뷰
    lateinit var sizebar: SeekBar
    lateinit var fontSizeEx: TextView
    lateinit var magnification: TextView

    lateinit var nickname : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_font_size)

        //뒤로가기 버튼 - 설정 화면(activity_setting.xml)으로 이동
        backBtn = findViewById(R.id.backBtnF)

        backBtn.setOnClickListener{
            var intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
            finish()
        }

        //폰트 크기 변경
        sizebar = findViewById(R.id.sizebar)
        fontSizeEx = findViewById(R.id.fontSizeEx)
        magnification = findViewById(R.id.magnification)
        nickname = findViewById(R.id.nickname)

        //폰트 조절 Progress Bar
        val progress: Int = (application as textApplication).getProgress()
        sizebar.progress = progress

        val mag : String = (application as textApplication).getMagnification()
        magnification.text = mag

        //조절 폰트 적용
        //폰트 사이즈 설정
        val font: Float = (application as textApplication).getSize()
        nickname.textSize = font
        fontSizeEx.textSize = font
        magnification.textSize = font

        sizebar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                var progress = progress

                when(progress){
                    in 0..5 -> {
                        sizebar.progress = 0
                        fontSizeEx.setTextSize(Dimension.SP, 10F)
                        magnification.text = "x 0.7"
                        (application as textApplication).setMagnification("x 0.7")
                        (application as textApplication).setProgress(0)
                        (application as textApplication).setSize(10F)
                    }
                    in 6..15 -> {
                        sizebar.progress = 10
                        fontSizeEx.setTextSize(Dimension.SP, 12F)
                        magnification.text = "x 0.8"
                        (application as textApplication).setMagnification("x 0.8")
                        (application as textApplication).setProgress(10)
                        (application as textApplication).setSize(12F)
                    }
                    in 16..25 -> {
                        sizebar.progress = 20
                        fontSizeEx.setTextSize(Dimension.SP, 14F)
                        magnification.text = "x 0.9"
                        (application as textApplication).setMagnification("x 0.9")
                        (application as textApplication).setProgress(20)
                        (application as textApplication).setSize(14F)
                    }
                    in 26..35 -> {
                        sizebar.progress = 30
                        fontSizeEx.setTextSize(Dimension.SP, 16F)
                        magnification.text = "x 1.0"
                        (application as textApplication).setMagnification("x 1.0")
                        (application as textApplication).setProgress(30)
                        (application as textApplication).setSize(16F)
                    }
                    in 36..45 -> {
                        sizebar.progress = 40
                        fontSizeEx.setTextSize(Dimension.SP, 18F)
                        magnification.text = "x 1.1"
                        (application as textApplication).setMagnification("x 1.1")
                        (application as textApplication).setProgress(40)
                        (application as textApplication).setSize(18F)
                    }
                    in 46..55 -> {
                        sizebar.progress = 50
                        fontSizeEx.setTextSize(Dimension.SP, 20F)
                        magnification.text = "x 1.2"
                        (application as textApplication).setMagnification("x 1.2")
                        (application as textApplication).setProgress(50)
                        (application as textApplication).setSize(20F)
                    }
                    in 56..60 -> {
                        sizebar.progress = 60
                        fontSizeEx.setTextSize(Dimension.SP, 22F)
                        magnification.text = "x 1.3"
                        (application as textApplication).setMagnification("x 1.3")
                        (application as textApplication).setProgress(60)
                        (application as textApplication).setSize(22F)
                    }
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                // TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                // TODO("Not yet implemented")
            }
        })
    }
}