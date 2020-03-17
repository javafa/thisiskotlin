package com.example.sqlite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orm_memo")
class RoomMemo {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo
    var no:Long?=null
    @ColumnInfo
    var content:String=""
    @ColumnInfo(name="date")
    var datetime:Long=0
    constructor(content:String, datetime:Long) {
        this.content = content
        this.datetime = datetime
    }
}