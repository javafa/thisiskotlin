package net.flow9.calllog

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_phonebook_layout.view.*

data class Phone(val id:String?, val name:String?, val phone:String?)

class PhoneAdapter(val list:List<Phone>) : RecyclerView.Adapter<PhoneAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_phonebook_layout, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val phone = list[position]
        holder.setPhone(phone)
    }


    @SuppressLint("MissingPermission")
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mPhone:Phone? = null
        init {
            itemView.btnPhone.setOnClickListener {
                mPhone?.phone.let { phoneNumber ->
                    val uri = Uri.parse("tel:${phoneNumber.toString()}")
                    val intent = Intent(Intent.ACTION_CALL, uri)
                    itemView.context.startActivity(intent)
                }
            }

        }

        fun setPhone(phone:Phone) {
            this.mPhone = phone
            itemView.textName.text = phone.name?:""
            itemView.textPhone.text = phone.phone?:""
        }
    }
}
