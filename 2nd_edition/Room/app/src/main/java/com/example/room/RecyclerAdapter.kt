package com.example.room

import java.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.room.databinding.ItemRecyclerBinding

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.Holder>() {
    var helper:RoomHelper? = null
    var listData = mutableListOf<RoomMemo>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }
    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val memo = listData.get(position)
        holder.setRoomMemo(memo)
    }

    inner class Holder(val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        var mRoomMemo:RoomMemo? = null
        init {
            binding.buttonDelete.setOnClickListener {
                helper?.roomMemoDao()?.delete(mRoomMemo!!)
                listData.remove(mRoomMemo)
                notifyDataSetChanged()
            }
        }
        fun setRoomMemo(memo:RoomMemo) {
            binding.textNo.text = "${memo.no}"
            binding.textContent.text = memo.content
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
            // 날짜 포맷은 SimpleDateFormat으로 설정합니다.
            binding.textDatetime.text = "${sdf.format(memo.datetime)}"

            this.mRoomMemo = memo
        }
    }
}

