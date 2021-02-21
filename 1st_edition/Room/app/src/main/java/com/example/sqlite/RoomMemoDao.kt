package com.example.sqlite

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface RoomMemoDao {
    @Query("select * from orm_memo")
    fun getAll(): List<RoomMemo>
    @Insert(onConflict = REPLACE)
    fun insert(memo: RoomMemo)
    @Delete
    fun delete(memo:RoomMemo)
}