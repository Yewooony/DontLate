package com.example.dontlate

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
open class EditAccountActivity : AppCompatActivity() {

    //기본 제공 버튼
    lateinit var editFinBtn: Button
    lateinit var backBtn: Button

    // 로그인 정보 받아오기 : 데이터베이스
    lateinit var userDbManager : userDBManager
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var str_name : String
    lateinit var str_id : String
    lateinit var str_password : String

    //정보 수정 시 팝업 제공
    var dialog : CustomDialog? = null

    //프로필 사진
    lateinit var img_user: ImageView
    lateinit var selectBtn: ImageButton

    //폰트 사이즈 변경
    lateinit var nameText : TextView
    lateinit var pwCheckText : TextView
    lateinit var idText : TextView
    lateinit var editName : TextInputEditText
    lateinit var editPw : TextInputEditText
    lateinit var editId : TextInputEditText


    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_account)


        editFinBtn = findViewById(R.id.editFinBtn)
        backBtn = findViewById(R.id.backBtnEA)

        nameText = findViewById(R.id.nameText)
        editName = findViewById(R.id.editName)
        pwCheckText = findViewById(R.id.pwCheckText)
        editPw = findViewById(R.id.editPw)
        idText = findViewById(R.id.idText)
        editId = findViewById(R.id.editId)


        // 로그인 정보 받아오기
        val intent = intent
        val user_id = intent.getStringExtra("user_id").toString()

        // 데이터베이스 연결
        userDbManager = userDBManager(this@EditAccountActivity, "user_info", null, 1)
        var cursor : Cursor

        sqlitedb = userDbManager.readableDatabase
        cursor = sqlitedb.rawQuery("SELECT * FROM user_info WHERE ID = '$user_id';", null)

        while (cursor.moveToNext()) {
            str_id = cursor.getString(cursor.getColumnIndex("ID")).toString()
            str_password = cursor.getString(cursor.getColumnIndex("password")).toString()
            str_name = cursor.getString(cursor.getColumnIndex("name")).toString()
        }
        sqlitedb.close()

        //회원 정보 반영
        editName.setText(str_name)
        editId.setText(str_id)
        editPw.setText(str_password)


        backBtn.setOnClickListener {
            var intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)
            finish()
        }


        //정보 수정 반영 다이얼로그
        dialog = CustomDialog(this)

        editFinBtn.setOnClickListener {
            var edt_name : String = editName.text.toString()
            var edt_password : String = editPw.text.toString()

            if(edt_name == str_name && edt_password == str_password) {
                Toast.makeText(this@EditAccountActivity, "변경사항이 없습니다.", Toast.LENGTH_SHORT).show()

                /*
                var intent = Intent(this, AccountActivity::class.java)
                startActivity(intent)
                 */

            } else {
                sqlitedb = userDbManager.writableDatabase
                sqlitedb.execSQL("UPDATE user_info SET name = '$edt_name', password = '$edt_password' WHERE ID = '$user_id'")
                sqlitedb.close()
                Toast.makeText(this@EditAccountActivity, "변경되었습니다.", Toast.LENGTH_SHORT).show()


                var intent = Intent(this, AccountActivity::class.java)
                startActivity(intent)
            }
        }



        //프로필 사진 변경 코드
        img_user = findViewById(R.id.img_user5)
        img_user.setImageResource(R.drawable.profile)

        selectBtn = findViewById(R.id.selectBtn)

        selectBtn.setOnClickListener {
            var pop = PopupMenu(this, img_user)
            menuInflater.inflate(R.menu.profile_menu, pop.menu)

            pop.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.one -> {
                        //실제 카메라 구동 코드는 함수로 처리
                        captureCamera()
                        img_user.setBackgroundResource(R.drawable.profile_shape)
                    }
                    R.id.two -> {
                        //갤러리에 관한 권한을 받아오는 코드
                        getAlbum()
                    }
                    R.id.three -> img_user.setImageResource(R.drawable.profile)
                }
                true
            }
            pop.show()
        }

        //popup 메뉴 생성 코드
        @Override
        fun onCreateOptionsMenu(menu: Menu): Boolean {
            menuInflater.inflate(R.menu.profile_menu, menu)
            return true
        }

        @Override
        fun onOptionsItemSelected(item: MenuItem): Boolean {
            var id: Int = item.itemId
            if (id == 1) {
                return true
            }
            return super.onOptionsItemSelected(item)
        }


        val font: Float = (application as textApplication).getSize()
        nameText.textSize = font
        editName.textSize = font
        pwCheckText.textSize = font
        editPw.textSize = font
        idText.textSize = font
        editId.textSize = font
        editFinBtn.textSize = font
    }

    // 카메라 촬영 프로필 업데이트 : 상수 지정
    private val PERMISSION_CAMERA : Int = 1111
    private val REQUEST_CAMERA : Int = 2222
    lateinit var realUri : Uri

    // 앨범 사진 프로필 업데이트 : 상수 지정
    private val PERMISSION_Album = 101 // 앨범 권한 처리
    private val REQUEST_STORAGE = 3333


    // 내부 함수로 처리하는 사진을 촬영할 수 있도록 하는 함수 구현
    // 사진 촬영 함수
    fun captureCamera() {
        requirePermissions(arrayOf(android.Manifest.permission.CAMERA), PERMISSION_CAMERA)
    }

    //갤러리에서 사진이 추가되고 선택할 수 있도록 하는 함수 구현
    //profile_menu에서 갤러리를 클릭하면 getAlbum 함수 호출
    private fun getAlbum(){
        requirePermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_Album)
    }


    /** 자식 액티비티에서 권한 요청 시 직접 호출하는 메소드
     * @param permissions 권한 처리를 할 권한 목록
     * @param requestCode 권한을 요청한 주체가 어떤 것인지 구분하기 위함.
     * */
    fun requirePermissions(permissions: Array<String>, requestCode: Int) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            permissionGranted(requestCode)
        } else {
            // isAllPermissionsGranted : 권한이 모두 승인되었는지 여부 저장
            // all 메서드를 사용하면 배열 속에 들어있는 모든 값을 체크할 수 있다.
            val isAllPermissionsGranted =
                permissions.all { checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED }
            if(isAllPermissionsGranted) {
                permissionGranted(requestCode)
            } else {
                // 사용자에 권한 승인 요청
                ActivityCompat.requestPermissions(this, permissions, requestCode)
            }
        }
    }

    /** 사용자가 권한을 승인하거나 거부한 다음에 호출되는 메서드
     * @param requestCode 요청한 주체를 확인하는 코드
     * @param permissions 요청한 권한 목록
     * @param grantResults 권한 목록에 대한 승인/미승인 값, 권한 목록의 개수와 같은 수의 결괏값이 전달된다.
     * */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            permissionGranted(requestCode)
        } else {
            permissionDenied(requestCode)
        }
    }

    private fun permissionGranted(requestCode: Int) {
        when(requestCode) {
            PERMISSION_CAMERA -> openCamera()
            PERMISSION_Album -> openGallery()
        }
    }

    private fun permissionDenied(requestCode: Int) {
        when(requestCode) {
            PERMISSION_CAMERA -> Toast.makeText(
                this,
                "카메라 권한을 승인해야 카메라를 사용할 수 있습니다.",
                Toast.LENGTH_LONG
            ).show()

            PERMISSION_Album -> Toast.makeText(
                this,
                "저장소 권한을 승인해야 앨범에서 이미지를 불러올 수 있습니다.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    // 카메라를 이용해 찍은 값을 Uri로 받기 위한 코드
    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        createImageUri(newFileName(), "image/jpg")?.let { uri ->
            realUri = uri
            // MediaStore.EXTRA_OUTPUT을 Key로 하여 Uri를 넘겨주면
            // 일반적인 Camera App은 이를 받아 내가 지정한 경로에 사진을 찍어서 저장시킨다.
            intent.putExtra(MediaStore.EXTRA_OUTPUT, realUri)
            startActivityForResult(intent, REQUEST_CAMERA)
        }
    }

    // 앨범에서 선택된 이미지를 Uri로 받기 위한 코드
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, REQUEST_STORAGE)
    }

    private fun newFileName() : String {
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
        val filename = sdf.format(System.currentTimeMillis())
        return "$filename.jpg"
    }

    private fun createImageUri(filename: String, mimeType: String) : Uri? {
        var values = ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)
        return this.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    }

    /** 카메라 및 앨범 Intent 결과
     * */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK) {
            when(requestCode) {
                REQUEST_CAMERA -> {
                    realUri?.let { uri ->
                        Glide.with(this)
                            .load(uri)
                            .apply(RequestOptions.centerCropTransform().circleCrop())
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(img_user)
                    }
                }

                REQUEST_STORAGE -> {
                    data?.data?.let { uri ->
                        Glide.with(this)
                            .load(uri)
                            .apply(RequestOptions.centerCropTransform().circleCrop())
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(img_user)
                    }
                }
            }
        }
    }
}