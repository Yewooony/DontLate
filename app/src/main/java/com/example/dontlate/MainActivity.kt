package com.example.dontlate


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dontlate.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    //프래그먼트, 뷰 바인딩
    private lateinit var binding : ActivityMainBinding

    //회원 정보 받아오기
    lateinit var user_id : String
    lateinit var user_name : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 로그인 정보 받아오기
        val intent = intent
        user_id = intent.getStringExtra("user_id").toString()
        user_name = intent.getStringExtra("user_name").toString()

        initBottomNavigation()

    }


    /**
     * 네비게이션 초기화 함수
     * 각 Fragment에 유저 정보를 전달하기 위한 코드 :
     *
     *      var (FragmentName) = (FragmentName)()
            var bundle = Bundle()

            bundle.putString("user_id", user_id)
            bundle.putString("user_name", user_name
            myPageFragment.arguments = bundle
     *
     * user_id 정보를 전달하여 각 fragment에서 db 활용
     * */
    private fun initBottomNavigation() {
        binding.bottomNavi.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    //네비게이션 홈 버튼 클릭
                    R.id.menu_home -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.mainFrameLayout, HomeFragment())
                            .addToBackStack(null)
                            .commit();
                    }

                    //네비게이션 마이 페이지 버튼 클릭
                    R.id.menu_mypage -> {
                        var myPageFragment = MyPageFragment()
                        var bundle = Bundle()

                        bundle.putString("user_id", user_id)
                        bundle.putString("user_name", user_name)
                        myPageFragment.arguments = bundle // fragment의 arguments에 데이터를 담은 bundle을 넘겨줌

                        supportFragmentManager.beginTransaction()
                            .replace(R.id.mainFrameLayout, myPageFragment)
                            .addToBackStack(null).commit()
                    }

                    //네비게이션 약속 생성 버튼 클릭
                    R.id.menu_social -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.mainFrameLayout, SocialFragment())
                            .addToBackStack(null)
                            .commit()
                    }
                }

                true
            }

            //기본 값 - 홈으로 설정
            selectedItemId = R.id.menu_home
        }
    }


}