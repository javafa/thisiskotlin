package net.flow9.calllog

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_phone_book.view.*
import net.flow9.phonebookinfragment.Phone
import net.flow9.phonebookinfragment.PhoneAdapter

class PhoneBookFragment : Fragment() {

    lateinit var mAdapter: PhoneAdapter
    var phoneList = mutableListOf<Phone>()
    var searchText = ""
    var sortText = "asc"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_phone_book, container, false)
        setContentView(view)
        return view
    }

    fun setContentView(view:View) {
        phoneList.addAll(getPhoneNumbers(sortText, searchText))
        mAdapter = PhoneAdapter(phoneList)
        view.recycler.adapter = mAdapter
        view.recycler.layoutManager = LinearLayoutManager(context)
    }

    @SuppressLint("MissingPermission")
    fun getPhoneNumbers(sort:String, name:String) : List<Phone>{
        val list = mutableListOf<Phone>()

        // 컨텐트 리졸버로 데이터를 가져온다
        // 1. 주소, 컬럼, 조건

        val phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI

        val projections = arrayOf(ContactsContract.CommonDataKinds.Phone.CONTACT_ID
            , ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            , ContactsContract.CommonDataKinds.Phone.NUMBER)
        var where:String? = null
        // 컨텐트리졸버의 조건식 = 데이터베이스 쿼리랑 동일
        // 비교구문 : 컬럼명 = ? and 컬럼명2 = ?
        // LIKE구문 : 컬럼명 like '%길%'
        var whereValues:Array<String>? = null
        if(name.isNotEmpty()) {
            where = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "= ?"
            whereValues = arrayOf(name)
        }
        val optionSort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " $sort"

        context?.run {
            val cursor = contentResolver.query(phoneUri, projections, where, whereValues, optionSort)
            // 4. 반복문으로 아이디와 이름을 가져오면서 전화번호 조회 쿼리를 한번 더 돌린다.
            while(cursor?.moveToNext() == true) {
                val id = cursor?.getString(0)
                val name = cursor?.getString(1)
                var number = cursor?.getString(2)
                // 개별 전화번호 데이터 생성
                val phone = Phone(id, name, number)
                // 결과목록에 더하기
                list.add(phone)
            }
        }
        return list
    }
}