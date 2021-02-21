package net.flow9.contactsgroup

import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_group.view.*

class GroupFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_group, container, false)
        setContentView(view)
        return view
    }

    private fun setContentView(view:View) {
        val adapter = GroupAdapter(getGroupList())
        view.recycler.adapter = adapter
        view.recycler.layoutManager = LinearLayoutManager(context)
    }

    private fun getGroupList() : List<GroupItem> {
        val result = mutableListOf<GroupItem>()

        val uri = ContactsContract.Groups.CONTENT_URI
        val proj = arrayOf(ContactsContract.Groups._ID,
                            ContactsContract.Groups.TITLE)
        context?.run {
            val cursor = contentResolver.query(uri, proj, null, null, null)
            while(cursor?.moveToNext() == true) {
                val id = cursor.getString(0)
                val title = cursor.getString(1)
                val group = GroupItem(id, title)
                result.add(group)
            }
        }

        return result
    }
}

