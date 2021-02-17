package net.flow9.contactsgroup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_contact.view.*

data class ContactItem(val id:String, val name:String, val phone:String?)

class ContactAdapter(groupList:List<ContactItem>) : RecyclerView.Adapter<ContactAdapter.Holder>(){
    private val list = groupList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setItem(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view) {
        fun setItem(item:ContactItem){
            view.name.text = item.name
            view.phone.text = item.phone
        }
    }
}