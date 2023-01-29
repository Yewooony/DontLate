package com.example.dontlate

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


open class CustomDialog(context: Context) : AppCompatActivity() {

    private lateinit var context : Context

    private val dialog = Dialog(context)
    private lateinit var body : TextView
    private lateinit var confirm : Button
    private lateinit var cancel : Button
    val Tag = "로그 "

    fun start(bodyMessage: String, mode: Int, dialogMode: Int, context: Context) {
        //dialogMode = 1 (확인 버튼 한 개만 존재)
        //dialogMode = 2 (회원 탈퇴 다이얼로그)
        //dialogMode = 3 (프로필 수정 완료 다이얼로그)
        //mode 변수 값에 따라 클릭 리스너 이벤트 활동이 달라짐 (소스 참고)
        //mode 값에 따라서 클릭 리스너를 세팅합니다!

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // 여백 제거

        when(dialogMode) {
            1 -> { // 확인 버튼 한 개 존재
                dialog.setContentView(R.layout.idcheck_popup)
                body = dialog.findViewById(R.id.mainText3)
                body.text = bodyMessage

                confirm = dialog.findViewById(R.id.idCheck)

                confirm.setOnClickListener {
                    when(mode) {
                        1 -> {
                            dialog.dismiss()
                        }
                    }
                }
            }

            2 -> { // 회원 탈퇴 다이얼로그
                dialog.setContentView(R.layout.withdrawal_popup)
                body = dialog.findViewById(R.id.mainText2)
                body.text = bodyMessage

                confirm = dialog.findViewById(R.id.checkBtn)
                cancel = dialog.findViewById(R.id.cancelBtn)

                cancel.setOnClickListener {//취소 버튼
                    dialog.dismiss()
                }

                confirm.setOnClickListener {//확인 버튼
                    when(mode) {
                        1 -> {
                            var intent = Intent(context, loginActivity::class.java)
                            context.startActivity(intent)
                            finish()
                        }
                    }
                }
            }

            3 -> { // 수정 완료 다이얼로그
                dialog.setContentView(R.layout.edit_popup)
                body = dialog.findViewById(R.id.mainText)
                body.text = bodyMessage

                confirm = dialog.findViewById(R.id.checkBtn)
                cancel = dialog.findViewById(R.id.cancelBtn)

                cancel.setOnClickListener {
                    dialog.dismiss()
                }

                confirm.setOnClickListener {
                    when(mode) {
                        1 -> {
                            var intent = Intent(context, AccountActivity::class.java)
                            context.startActivity(intent)
                            finish()
                        }
                    }
                }
            }

        }
        dialog.setCancelable(false) //다이얼로그 이외의 화면 터치 시 닫히지 않음
        dialog.show()
    }
}