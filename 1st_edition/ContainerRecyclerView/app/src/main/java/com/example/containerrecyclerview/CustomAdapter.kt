package com.example.containerrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_recycler.view.*
import java.text.SimpleDateFormat

class CustomAdapter : RecyclerView.Adapter<Holder>() {
    var listData = mutableListOf<Memo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val memo = listData.get(position)
        holder.setMemo(memo)
    }
}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    init {
        itemView.setOnClickListener {
            Toast.makeText(itemView?.context
                ,"클릭된 아이템=${itemView.textTitle.text}"
                , Toast.LENGTH_LONG).show()
        }
    }

    fun setMemo(memo:Memo) {
        itemView.textNo.text = "${memo.no}"
        itemView.textTitle.text = memo.title

        var sdf = SimpleDateFormat("yyyy/MM/dd")
        var formattedDate = sdf.format(memo.timestamp)
        itemView.textDate.text = formattedDate
    }
}

