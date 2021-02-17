package kr.co.hanbit.miniquiz8_3_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var helper:RoomHelper? = null
    // 수정할 데이터 저장
    var updateMemo:RoomMemo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        helper = Room.databaseBuilder(this, RoomHelper::class.java, "room_memo")
            .allowMainThreadQueries()
            .build()
        val adapter = RecyclerAdapter()

        adapter.helper = helper
        adapter.listData.addAll(helper?.roomMemoDao()?.getAll() ?: mutableListOf())
        // 수정을 위해 메인액티비티 연결
        adapter.mainActivity = this

        recyclerMemo.adapter = adapter
        recyclerMemo.layoutManager = LinearLayoutManager(this)

        buttonSave.setOnClickListener {
            // 수정 체크 추가
            if (updateMemo != null) {
                updateMemo?.content = editMemo.text.toString()
                helper?.roomMemoDao()?.update(updateMemo!!)
                refreshAdapter(adapter)
                cancelUpdate()
            }else if (editMemo.text.toString().isNotEmpty()) {
                val memo = RoomMemo(editMemo.text.toString(), System.currentTimeMillis())
                helper?.roomMemoDao()?.insert(memo)
                refreshAdapter(adapter)
                editMemo.setText("")
            }
        }

        buttonCanel.setOnClickListener {
            cancelUpdate()
        }
    }

    fun setUpdate(memo:RoomMemo){
        updateMemo = memo

        editMemo.setText(updateMemo!!.content)
        buttonCanel.visibility = View.VISIBLE
        buttonSave.text = "수정"
    }

    fun cancelUpdate() {
        updateMemo = null

        editMemo.setText("")
        buttonCanel.visibility = View.GONE
        buttonSave.text = "저장"
    }

    fun refreshAdapter(adapter:RecyclerAdapter) {
        adapter.listData.clear()
        adapter.listData.addAll(helper?.roomMemoDao()?.getAll() ?: mutableListOf())
        adapter.notifyDataSetChanged()
    }
}
