package com.example.networkretrofit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_recycler.view.*

class CustomAdapter:RecyclerView.Adapter<Holder>() {
    var userList = mutableListOf<Repository>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val user = userList.get(position)
        holder.setUser(user)
    }
}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun setUser(user:Repository) {
        itemView.textName.text = user.name
        itemView.textId.text = user.node_id
        Glide.with(itemView).load(user.owner.avatar_url).into(itemView.imageAvatar)
    }
}
