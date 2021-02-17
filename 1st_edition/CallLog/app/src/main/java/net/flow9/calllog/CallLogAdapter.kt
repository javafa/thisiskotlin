package net.flow9.calllog

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_calllog_layout.view.*

data class Call(val id:String?, val name:String?, val phone:String?, val photoUri:String?)

class CallLogAdapter(val list:List<Call>) : RecyclerView.Adapter<CallLogAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_calllog_layout, parent, false)
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
        var mCall: Call? = null
        init {
            itemView.btnPhone.setOnClickListener {
                mCall?.phone.let { phoneNumber ->
                    val uri = Uri.parse("tel:${phoneNumber.toString()}")
                    val intent = Intent(Intent.ACTION_CALL, uri)
                    itemView.context.startActivity(intent)
                }
            }

            itemView.btnSms.setOnClickListener {
                mCall?.phone.let { phoneNumber ->
                    val uri = Uri.parse("sms:${phoneNumber}")
                    val intent = Intent(Intent.ACTION_SENDTO, uri)
                    itemView.context.startActivity(intent)
                }
            }
        }

        fun setPhone(call: Call) {
            mCall = call
            itemView.textName.text = call.name?:""
            itemView.textPhone.text = call.phone?:""
            call.photoUri?.let {photoUri ->
                if(photoUri.isNotEmpty()){
                    val uri = Uri.parse(photoUri)
                    itemView.imageView.setImageURI(uri)
                }
            }
        }
    }
}
