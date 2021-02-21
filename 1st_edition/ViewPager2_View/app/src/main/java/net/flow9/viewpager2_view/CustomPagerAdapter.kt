package net.flow9.viewpager2_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_viewpager.view.*

class CustomPagerAdapter : RecyclerView.Adapter<Holder>(){
    var textList = listOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_viewpager, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return textList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val text = textList[position]
        holder.setText(text)
    }
}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun setText(text:String) {
        itemView.textView.text = text
    }
}


