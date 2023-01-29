package com.example.dontlate

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.idcheck_popup.*

class joinActivity : AppCompatActivity() {

    lateinit var backBtn: Button
    lateinit var loginBtn: Button

    //회원가입 기능 구현
    lateinit var userDbManager: userDBManager
    lateinit var personnelDBManager: personnelDBManager
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var startBtn: Button
    lateinit var idInput : TextInputEditText
    lateinit var passwordInput : TextInputEditText
    lateinit var nameInput : TextInputEditText

    //중복 확인
    lateinit var checkBtn: AppCompatButton
    var use : Int = 0
    var validate : Boolean = false

    //중복 시 팝업 제공
    var dialog : CustomDialog? = null
    
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        backBtn = findViewById(R.id.backBtnA)
        loginBtn = findViewById(R.id.loginBtn)

        //회원가입 기능 구현
        startBtn = findViewById(R.id.startBtn)
        idInput = findViewById(R.id.idInput)
        passwordInput = findViewById(R.id.passwordInput)
        nameInput = findViewById(R.id.nameInput)

        //중복 확인 다이얼로그
        dialog = CustomDialog(this)

        // 데이터베이스 연결
        userDbManager = userDBManager(this@joinActivity, "user_info", null, 1)
        var cursor : Cursor

        //중복 확인
        checkBtn = findViewById(R.id.checkBtn)

        checkBtn.setOnClickListener {
            var str_ID : String = idInput.text.toString()
            var idCheck : String

            if(str_ID.isEmpty()) {
                Toast.makeText(this@joinActivity, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            sqlitedb = userDbManager.readableDatabase
            cursor = sqlitedb.rawQuery("SELECT * FROM user_info;", null)

            while (cursor.moveToNext()) {
                idCheck = cursor.getString(cursor.getColumnIndex("ID")).toString()

                if (str_ID == idCheck) {
                    validate = false
                    break
                } else
                    validate = true
            }

            if (validate) {
                checkBtn.setBackgroundDrawable(getDrawable(R.drawable.btn_clicked))
                checkBtn.text = "확인 완료"
                checkBtn.setTextColor(Color.parseColor("#5F893E"))
                Toast.makeText(this@joinActivity, "사용 가능한 ID입니다.", Toast.LENGTH_SHORT).show()
                use = 1
                idInput.isFocusable = false
                checkBtn.isEnabled = false
            } else {
                dialog!!.start("이미 중복된 ID입니다.", 1, 1, this@joinActivity)
            }

            cursor.close()
            sqlitedb.close()
            userDbManager.close()
        }

        //시작 버튼 리스너 설정
        startBtn.setOnClickListener{
            var str_name : String = nameInput.text.toString()
            var str_ID : String = idInput.text.toString()
            var str_password : String = passwordInput.text.toString()
            var str_profile : String = ""

            if(str_ID.isEmpty()) {
                Toast.makeText(this@joinActivity, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(str_password.isEmpty()) {
                Toast.makeText(this@joinActivity, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(str_name.isEmpty()) {
                Toast.makeText(this@joinActivity, "닉네임을 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            sqlitedb = userDbManager.writableDatabase
            if(checkBtn.text == "확인 완료" && use == 1) {
                sqlitedb.execSQL("INSERT INTO user_info VALUES ('" + str_name + "', '"
                        + str_ID + "', '" + str_password + "', '" + str_profile + "');")
                sqlitedb.close()

                /**
                 * 개인 데이터를 저장할 Database 생성
                 * DB 생성 시, 초기 데이터가 존재하지 않으면 테이블 생성 X
                 * 'example' 초기 데이터로 생성 후, 추후 삭제 예정
                 */
                personnelDBManager = personnelDBManager(this@joinActivity, str_ID, null, 1)
                sqlitedb = personnelDBManager.writableDatabase
                sqlitedb.execSQL("INSERT INTO $str_ID VALUES ('example', 'example', 'example', 'example')")
                Toast.makeText(this@joinActivity, "가입되었습니다.", Toast.LENGTH_SHORT).show()

                var intent = Intent(this, loginActivity::class.java)
                startActivity(intent)

                sqlitedb.close()
                userDbManager.close()
                personnelDBManager.close()
            } else {
                Toast.makeText(this@joinActivity, "아이디 중복 확인을 해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        //돌아 가기 버튼 클릭 리스너 설정
        backBtn.setOnClickListener{
            var intent = Intent(this, StartupActivity::class.java)
            startActivity(intent)
        }

        //로그인 버튼 클릭 리스너 설정
        loginBtn.setOnClickListener{
            var intent = Intent(this, loginActivity::class.java)
            startActivity(intent)
        }
    }
}