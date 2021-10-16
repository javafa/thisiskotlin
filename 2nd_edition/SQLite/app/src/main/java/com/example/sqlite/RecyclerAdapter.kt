package com.example.sqlite

import java.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlite.databinding.ItemRecyclerBinding

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.Holder>() {
    var helper:SqliteHelper? = null
    var listData = mutableListOf<Memo>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }
    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val memo = listData.get(position)
        holder.setMemo(memo)
    }

    inner class Holder(val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        var mMemo:Memo? = null
        init {
            binding.buttonDelete.setOnClickListener {
                helper?.deleteMemo(mMemo!!)
                listData.remove(mMemo)
                notifyDataSetChanged()
            }
        }
        fun setMemo(memo:Memo) {
            binding.textNo.text = "${memo.no}"
            binding.textContent.text = memo.content
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
            // 날짜 포맷은 SimpleDateFormat으로 설정합니다.
            binding.textDatetime.text = "${sdf.format(memo.datetime)}"

            this.mMemo = memo
        }
    }
}

