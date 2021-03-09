package com.example.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.room.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    var helper: RoomHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        helper = Room.databaseBuilder(this, RoomHelper::class.java, "room_memo")
                .addMigrations(MigrateDatabase.MIGRATE_1_2)
            .allowMainThreadQueries()
            .build()

        val adapter = RecyclerAdapter()
        adapter.helper = helper

        adapter.listData.addAll(helper?.roomMemoDao()?.getAll()?: listOf())
        binding.recyclerMemo.adapter = adapter
        binding.recyclerMemo.layoutManager = LinearLayoutManager(this)

        binding.buttonSave.setOnClickListener {
            if (binding.editMemo.text.toString().isNotEmpty()) {
                val memo = RoomMemo(binding.editMemo.text.toString(), System.currentTimeMillis())
                helper?.roomMemoDao()?.insert(memo)
                adapter.listData.clear()
                adapter.listData.addAll(helper?.roomMemoDao()?.getAll() ?: listOf())

                adapter.notifyDataSetChanged()
                binding.editMemo.setText("")
            }
        }
    }
}

//룸 변경사항 적용하기
object MigrateDatabase {
    val MIGRATE_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            val alter = "ALTER table room_memo add column new_title text"
            database.execSQL(alter)
        }
    }
}
