package net.flow9.calllog

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.CallLog
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_calllog.view.*


class CallLogFragment : Fragment() {

    lateinit var mAdapter: CallLogAdapter
    var callList = mutableListOf<Call>()
    var searchText = ""
    var sortText = "asc"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calllog, container, false)
        setContentView(view)
        return view
    }

    fun setContentView(view:View) {
        callList.addAll(getPhoneNumbers(sortText, searchText))
        mAdapter = CallLogAdapter(callList)
        view.recycler.adapter = mAdapter
        view.recycler.layoutManager = LinearLayoutManager(context)
    }

    @SuppressLint("MissingPermission")
    fun getPhoneNumbers(sort:String, name:String) : List<Call>{
        val list = mutableListOf<Call>()

        val callLogUri = CallLog.Calls.CONTENT_URI
        var proj = arrayOf(CallLog.Calls.PHONE_ACCOUNT_ID, CallLog.Calls.CACHED_NAME, CallLog.Calls.NUMBER, CallLog.Calls.CACHED_PHOTO_URI)
        context?.run {
            val cursor = contentResolver.query(callLogUri, proj, null, null, null)
            while(cursor?.moveToNext() == true) {
                val id = cursor?.getString(0)
                val name = cursor?.getString(1)
                var number = cursor?.getString(2)
                var photo = cursor?.getString(3)

                val call = Call(id, name, number, photo)
                list.add(call)
            }
        }

        return list
    }
}