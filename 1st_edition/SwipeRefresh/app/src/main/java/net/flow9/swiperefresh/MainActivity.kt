package net.flow9.swiperefresh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_recycler.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = CustomAdapter()
        adapter.items.addAll(getMoreItems())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        swipe.setOnRefreshListener {
            adapter.items.addAll(0, getMoreItems())
            adapter.notifyDataSetChanged()
            swipe.isRefreshing = false
        }
    }

    private var count = 0

    private fun getMoreItems() : List<String> {
        val items = mutableListOf<String>()
        val limit = count + 10
        while(count < limit ) {
            val item = "item $count"
            items.add(0, item)
            count += 1
        }
        return items
    }
}

class CustomAdapter : RecyclerView.Adapter<CustomAdapter.Holder>() {
    val items = mutableListOf<String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.textView.text = items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
}

