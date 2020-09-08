package net.flow9.contactsgroup

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_contacts.*

class ContactsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        val groupId = intent.getStringExtra("groupId")?: ""
        val groupName = intent.getStringExtra("groupName")?: "없음"

        groupTitle.text = groupName

        progress(groupId)
    }

    private fun progress(groupId:String) {
        if(groupId.isNotEmpty()){
            val contactList = getContactIds(groupId)
            Log.d("Contacts", "$contactList")
            val phoneList = getPhoneList(contactList)
            val adapter = ContactAdapter(phoneList)

            recycler.adapter = adapter
            recycler.layoutManager = LinearLayoutManager(this)
        }
    }



    data class ContactId(val groupId:String, val rowId:String, val contactId:String)

    fun getContactIds(groupId:String) : List<ContactId> {
        val result = mutableListOf<ContactId>()

        val uri = ContactsContract.Data.CONTENT_URI
        val proj = arrayOf(
            ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID
            , ContactsContract.CommonDataKinds.GroupMembership.CONTACT_ID)

        val where = "${ContactsContract.Data.DATA1}=? and ${ContactsContract.Data.MIMETYPE}=?"
        val values = arrayOf(groupId, ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE)
        val cursor = contentResolver.query(uri, proj, where, values,null)

        while (cursor?.moveToNext() == true) {
            val rowId = cursor?.getString(0)
            val contactId = cursor?.getString(1)
            val contact = ContactId(groupId, rowId, contactId)
            result.add(contact)
        }
        return result
    }

    fun getPhoneList(contactIds:List<ContactId>) : List<ContactItem> {
        val result = mutableListOf<ContactItem>()
        if(contactIds.size < 1) return result
        val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val proj = arrayOf(
            ContactsContract.CommonDataKinds.Phone._ID
            , ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            ,ContactsContract.CommonDataKinds.Phone.NUMBER)

        var idArray = ""
        for(contact in contactIds) {
            idArray += "," + contact.contactId
        }
        idArray = idArray.substring(1) // 첫번째 쉼표를 지워야함

        val where = "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} in (${idArray})"
        val cursor = contentResolver.query(uri, proj, where, null,null)

        while (cursor?.moveToNext() == true) {
            val id = cursor?.getString(0)
            val name = cursor?.getString(1)
            val number = cursor?.getString(2)
            val item = ContactItem(id, name, number)
            result.add(item)
        }

        return result
    }
}