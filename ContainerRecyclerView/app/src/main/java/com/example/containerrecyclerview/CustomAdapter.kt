package com.example.containerrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.containerrecyclerview.databinding.ItemRecyclerBinding
import java.text.SimpleDateFormat

class CustomAdapter : RecyclerView.Adapter<Holder>() {
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
}

class Holder(val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            Toast.makeText(binding.root.context
                ,"클릭된 아이템=${binding.textTitle.text}"
                , Toast.LENGTH_LONG).show()
        }
    }

    fun setMemo(memo:Memo) {
        binding.textNo.text = "${memo.no}"
        binding.textTitle.text = memo.title

        var sdf = SimpleDateFormat("yyyy/MM/dd")
        var formattedDate = sdf.format(memo.timestamp)
        binding.textDate.text = formattedDate
    }
}

