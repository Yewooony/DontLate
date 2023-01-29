package com.example.dontlate

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.dontlate.MapActivity.Companion.SEARCH_RESULT_EXTRA_KEY
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dontlate.databinding.ActivityMainBinding
import com.example.dontlate.databinding.ActivitySearchBinding
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import com.example.dontlate.model.LocationLatLngEntity
import com.example.dontlate.model.SearchResultEntity
import com.example.dontlate.response.search.Poi
import com.example.dontlate.response.search.Pois
import com.example.dontlate.response.search.SearchPoiInfo
import com.example.dontlate.utility.RetrofitUtil

class SearchActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var nextBtn: Button
    private lateinit var backBtn: ImageButton

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    lateinit var binding: ActivitySearchBinding
    lateinit var adapter: SearchRecyclerAdapter

    // 키보드 가릴 때 사용
    lateinit var inputMethodManager: InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        job = Job()

        initAdapter()
        initViews()
        bindViews()
        initData()

        backBtn = findViewById(R.id.s18_back_btn)

        backBtn.setOnClickListener(BackClickListener())
    }

    internal inner class BackClickListener : View.OnClickListener {
        override fun onClick(v: View) {
            // backBtn Click 이벤트 처리 구간
            val intent = Intent(applicationContext, social17::class.java)
            startActivity(intent)
        }
    }

    private fun initAdapter() {
        adapter = SearchRecyclerAdapter()
    }

    private fun bindViews() = with(binding) {
        searchBarInputView.setOnKeyListener { v, keyCode, event ->
            when (keyCode) {
                KeyEvent.KEYCODE_ENTER -> {
                    searchKeyword(searchBarInputView.text.toString())

                    // 키보드 숨기기
                    hideKeyboard()

                    return@setOnKeyListener true
                }
            }
            return@setOnKeyListener false
        }
    }

    private fun hideKeyboard() {
        if (::inputMethodManager.isInitialized.not()) {
            inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        }
        inputMethodManager.hideSoftInputFromWindow(binding.searchBarInputView.windowToken, 0)
    }

    /*
    `with` scope function 사용
     */
    private fun initViews() = with(binding) {
        emptyResultTextView.isVisible = false
        recyclerView.adapter = adapter

        // 무한 스크롤 기능 구현
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                recyclerView.adapter ?: return

                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                val totalItemCount = recyclerView.adapter!!.itemCount - 1

                // 페이지 끝에 도달한 경우
                if (!recyclerView.canScrollVertically(1) && lastVisibleItemPosition == totalItemCount) {
                    loadNext()
                }
            }
        })
    }

    private fun loadNext() {
        if (binding.recyclerView.adapter?.itemCount == 0)
            return

        searchWithPage(adapter.currentSearchString, adapter.currentPage + 1)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initData() {
        adapter.notifyDataSetChanged()
    }

    private fun setData(searchInfo: SearchPoiInfo, keywordString: String) {

        val pois: Pois = searchInfo.pois
        // mocking data
        val dataList = pois.poi.map {
            SearchResultEntity(
                name = it.name ?: "빌딩명 없음",
                fullAddress = makeMainAddress(it),
                locationLatLng = LocationLatLngEntity(
                    it.noorLat,
                    it.noorLon
                )
            )
        }
        adapter.setSearchResultList(dataList) {
            Toast.makeText(
                this,
                "빌딩이름 : ${it.name}, 주소 : ${it.fullAddress} 위도/경도 : ${it.locationLatLng}",
                Toast.LENGTH_SHORT
            )
                .show()

            // map 액티비티 시작
            startActivity(Intent(this, MapActivity::class.java).apply {
                putExtra(SEARCH_RESULT_EXTRA_KEY, it)
            })
        }
        adapter.currentPage = searchInfo.page.toInt()
        adapter.currentSearchString = keywordString
    }

    private fun searchKeyword(keywordString: String) {
        searchWithPage(keywordString, 1)
    }

    private fun searchWithPage(keywordString: String, page: Int) {
        // 비동기 처리
        launch(coroutineContext) {
            try {
                // IO 스레드 사용
                withContext(Dispatchers.IO) {
                    val response = RetrofitUtil.apiService.getSearchLocation(
                        keyword = keywordString,
                        page = page
                    )
                    if (response.isSuccessful) {
                        val body = response.body()
                        // Main (UI) 스레드 사용
                        withContext(Dispatchers.Main) {
                            Log.e("response LSS", body.toString())
                            body?.let { searchResponse ->
                                setData(searchResponse.searchPoiInfo, keywordString)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // error 해결 방법
                // Permission denied (missing INTERNET permission?) 인터넷 권한 필요
                // 또는 앱 삭제 후 재설치
            } finally {

            }
        }
    }

    private fun makeMainAddress(poi: Poi): String =
        if (poi.secondNo?.trim().isNullOrEmpty()) {
            (poi.upperAddrName?.trim() ?: "") + " " +
                    (poi.middleAddrName?.trim() ?: "") + " " +
                    (poi.lowerAddrName?.trim() ?: "") + " " +
                    (poi.detailAddrName?.trim() ?: "") + " " +
                    poi.firstNo?.trim()
        } else {
            (poi.upperAddrName?.trim() ?: "") + " " +
                    (poi.middleAddrName?.trim() ?: "") + " " +
                    (poi.lowerAddrName?.trim() ?: "") + " " +
                    (poi.detailAddrName?.trim() ?: "") + " " +
                    (poi.firstNo?.trim() ?: "") + " " +
                    poi.secondNo?.trim()
        }
}