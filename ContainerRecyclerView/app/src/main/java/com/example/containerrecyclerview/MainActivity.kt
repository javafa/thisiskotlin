package com.example.containerrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 1. 데이터를 생성하고
        val data:MutableList<Memo> = loadData()
        // 2. 만든 데이터로 아답터를 생성하고
        var adapter = CustomAdapter()
        adapter.listData = data
        // 3. 리사이클러뷰에 아답터를 연결하고
        recyclerView.adapter = adapter
        // 4. 리사이클러뷰에 레이아웃매니저를 연결한다.

        recyclerView.layoutManager = LinearLayoutManager(this)

//        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//
//        recyclerView.layoutManager = GridLayoutManager(this, 3)
//
//        recyclerView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
//
//        recyclerView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL)

    }

    fun loadData() : MutableList<Memo> {

        val data:MutableList<Memo> = mutableListOf()
        for(no in 0..100){
            // 개별 데이터 생성
            val title = "혼공 코틀린 ${no+1}"
            val date = System.currentTimeMillis()
            // 100 개의 Memo 클래스를 생성
            var memo = Memo(no, title, date)
            // 데이터 배열에 담는다
            data.add(memo)
        }
        return data;
    }
}












