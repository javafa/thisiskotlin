package net.flow9.searchjaum

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import net.flow9.searchjaum.lib.KoreanTextMatcher

// 전화번호 데이터 클래스 ( 편의상 여기에 정의해둡니다)
data class Phone(val id:String?, val name:String?, val phone:String?)

class MainActivity : AppCompatActivity() {
    val permissions = arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE)
    lateinit var adapter:PhoneAdapter
    var list = mutableListOf<Phone>()
    var searchText = ""
    var sortText = "asc"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkAndStart()
    }

    fun startProcess() {
        setList()
        setSearchListener()
    }

    fun setList() {
        list.addAll(getPhoneNumbers())
        adapter = PhoneAdapter(list)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)
    }

    fun setSearchListener() {
        editSearch.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchText = s.toString()
                changeList()
            }
        })
    }


    fun changeList() {
        val newList = filterName(searchText)

        this.adapter.list = newList
        this.adapter.notifyDataSetChanged()
    }

    fun filterName(searchJaum:String) : List<Phone> {
        val newList = mutableListOf<Phone>()

        for( phone in this.list) {
            val match = KoreanTextMatcher.match(phone.name, searchJaum)
            if(match.success()){
                newList.add(phone)
            }
        }

        return newList
    }

    fun getPhoneNumbers() : List<Phone> {
        // 결과목록 미리 정의
        val list = mutableListOf<Phone>()
        val phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        // 2.1 전화번호에서 가져올 컬럼 정의
        val projections = arrayOf(ContactsContract.CommonDataKinds.Phone.CONTACT_ID
            , ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            , ContactsContract.CommonDataKinds.Phone.NUMBER)
        // 3. 테이블에서 주소록 데이터 쿼리
        val cursor = contentResolver.query(phoneUri, projections, null, null, null)

        while(cursor?.moveToNext()?:false) {
            val id = cursor?.getString(0)
            val name = cursor?.getString(1)
            var number = cursor?.getString(2)
            // 개별 전화번호 데이터 생성
            val phone = Phone(id, name, number)
            // 결과목록에 더하기
            list.add(phone)
        }
        // 결과목록 반환
        return list
    }

    // 권한처리 코드
    fun checkAndStart() {
        if( isLower23() || isPermitted()) {
            startProcess()
        } else {
            ActivityCompat.requestPermissions(this, permissions, 99)
        }
    }

    fun isLower23() : Boolean{
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isPermitted():Boolean {
        for(perm in permissions) {
            if(checkSelfPermission(perm) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == 99) {
            var check = true
            for(grant in grantResults) {
                if(grant != PackageManager.PERMISSION_GRANTED) {
                    check = false
                    break
                }
            }
            if(check) startProcess()
            else {
                Toast.makeText(this, "권한 승인을 하셔야지만 앱을 사용할 수 있습니다.", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
}
