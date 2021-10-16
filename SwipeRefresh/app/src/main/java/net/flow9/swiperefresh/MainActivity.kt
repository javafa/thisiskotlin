package net.flow9.swiperefresh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.flow9.swiperefresh.databinding.ActivityMainBinding
import net.flow9.swiperefresh.databinding.ItemRecyclerBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = CustomAdapter()
        adapter.items.addAll(getMoreItems())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.swipe.setOnRefreshListener {
            adapter.items.addAll(0, getMoreItems())
            adapter.notifyDataSetChanged()
            binding.swipe.isRefreshing = false
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
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.textView.text = items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class Holder(val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {}
}

