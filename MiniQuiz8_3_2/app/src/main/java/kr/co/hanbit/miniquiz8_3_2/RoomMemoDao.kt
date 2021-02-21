package kr.co.hanbit.miniquiz8_3_2

import androidx.room.*

@Dao
interface RoomMemoDao {
    @Query("select * from orm_memo")
    fun getAll(): List<RoomMemo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(memo: RoomMemo)

    @Delete
    fun delete(memo:RoomMemo)

    @Update
    fun update(memo:RoomMemo)
}