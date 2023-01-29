package com.example.dontlate

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.idcheck_popup.*

class loginActivity : AppCompatActivity() {

    lateinit var backBtn: Button
    lateinit var startBtn: Button

    // 로그인 기능 구현
    lateinit var userDbManager : userDBManager
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var idInput : TextInputEditText
    lateinit var passwordInput : TextInputEditText

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        backBtn = findViewById(R.id.backBtnA)
        startBtn = findViewById(R.id.startBtn)
        idInput = findViewById(R.id.idInput)
        passwordInput = findViewById(R.id.passwordInput)

        // 데이터베이스 연결
        userDbManager = userDBManager(this@loginActivity, "user_info", null, 1)
        var cursor : Cursor

        //뒤로 가기 버튼
        backBtn.setOnClickListener{
            var intent = Intent(this, StartupActivity::class.java)
            startActivity(intent)
        }

        //시작하기 버튼
        startBtn.setOnClickListener{
            var str_ID : String = idInput.text.toString()
            var str_password : String = passwordInput.text.toString()
            var user_name : String
            var user_id : String
            var pwCheck : String

            // 아이디 입력창이 비어있을 경우
            if(str_ID.isEmpty()) {
                Toast.makeText(this@loginActivity, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 비밀번호 입력창이 비어있을 경우
            if(str_password.isEmpty()) {
                Toast.makeText(this@loginActivity, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            sqlitedb = userDbManager.readableDatabase
            cursor = sqlitedb.rawQuery("SELECT * FROM user_info WHERE ID = '$str_ID';", null)

            while (cursor.moveToNext()) {
                user_id = cursor.getString(cursor.getColumnIndex("ID")).toString()
                pwCheck = cursor.getString(cursor.getColumnIndex("password")).toString()
                user_name = cursor.getString(cursor.getColumnIndex("name")).toString()

                if(pwCheck == str_password){
                    var intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("user_id", user_id)
                    intent.putExtra("user_name",user_name)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@loginActivity, "아이디 또는 비밀번호를 잘못 입력하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}