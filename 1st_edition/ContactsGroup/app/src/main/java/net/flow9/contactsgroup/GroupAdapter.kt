package net.flow9.contactsgroup

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_group.view.*

data class GroupItem(val id:String, val name:String)

class GroupAdapter(groupList:List<GroupItem>) : RecyclerView.Adapter<GroupAdapter.Holder>(){
    private val list = groupList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_group, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setItem(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view) {
        var group:GroupItem? = null
        init {
            view.setOnClickListener {
                val intent = Intent(view.context, ContactsActivity::class.java)
                intent.putExtra("groupId", group?.id)
                intent.putExtra("groupName", group?.name)
                view.context.startActivity(intent)
            }
        }

        fun setItem(item:GroupItem){
            group = item
            view.groupId.text = item.id
            view.groupName.text = item.name
        }
    }
}