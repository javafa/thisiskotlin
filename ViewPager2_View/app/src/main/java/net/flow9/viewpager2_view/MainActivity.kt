package net.flow9.viewpager2_view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_viewpager.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val titles = listOf("View A", "View B", "View C", "View D")
        val data = listOf("뷰A", "뷰B", "뷰C", "뷰D")

        val adapter = CustomAdapter(data)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
            tab.text = titles[position]
        }.attach()
    }
}

class CustomAdapter(val data:List<String>) : RecyclerView.Adapter<Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_viewpager, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val text = data[position]
        holder.setText(text)
    }
}

class Holder(itemView:View) : RecyclerView.ViewHolder(itemView) {
    fun setText(text:String) {
        itemView.textView.text = text
    }
}