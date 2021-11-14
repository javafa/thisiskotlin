package net.flow9.thisiskotlin.firebasechat

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import net.flow9.thisiskotlin.firebasechat.databinding.ActivityMainBinding
import net.flow9.thisiskotlin.firebasechat.model.User

class MainActivity : AppCompatActivity() {
    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}
    val database = Firebase.database("https://this-is-android-with-kot-df246-default-rtdb.asia-southeast1.firebasedatabase.app")
    val usersRef  = database.getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        with(binding) {
            binding.btnSignin.setOnClickListener { signin() }
            binding.btnSignup.setOnClickListener { signup() }
        }
    }

    fun signup() {
        with(binding) {
            // 1. 입력된 값을 가져오고
            val id = editId.text.toString()
            val password = editPassword.text.toString()
            val name = editName.text.toString()

            // 2. 모두 값이 있으면
            if(id.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()) {
                usersRef.child(id).get().addOnSuccessListener {
                    // 2.1 먼저 아이디가 존재 하는지 검사 후 있으면
                    if(it.exists()) {
                        Toast.makeText(baseContext,
                            "아이디가 존재합니다.",
                            Toast.LENGTH_LONG)
                            .show()
                    } else { // 2.2 없으면 저장후 자동 로그인
                        val user = User(id, password, name)
                        usersRef.child(id).setValue(user)
                        signin()
                    }
                }
            // 3. 입력 필드가 비었으면
            } else {
                Toast.makeText(baseContext,
                    "아이디, 비밀번호 별명을 모두 입력해야 합니다.",
                    Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    fun signin() {
        with(binding) {
            // 1. 입력 된 값을 가져오고
            val id = editId.text.toString()
            val password = editPassword.text.toString()

            if(id.isNotEmpty() && password.isNotEmpty()) {
                // 2. 아이디로 User 데이터 가져오기
                usersRef.child(id).get().addOnSuccessListener {
                    // 2.1 id 존재 확인
                    if(it.exists()) {
                        it.getValue(User::class.java)?.let { user ->
                            // 2.1.1 비밀번호 비교 후 같으면 채팅방 목록이동
                            if(user.password == password) {
                                sendFcmToken(user)
                                goChatroomList(user.id, user.name)
                            } else {
                                Toast.makeText(baseContext, "비밀번호가 다릅니다.", Toast.LENGTH_LONG).show()
                            }
                        }
                    } else {
                        Toast.makeText(baseContext, "아이디가 없습니다.", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(baseContext,
                    "아이디, 비밀번호를  입력해야 합니다.",
                    Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    fun goChatroomList(userId: String, userName: String) {
        val intent = Intent(this, ChatListActivity::class.java)
        intent.putExtra("userId", userId)
        intent.putExtra("userName", userName)
        startActivity(intent)
    }

    fun sendFcmToken(user: User) {
        val shared = getSharedPreferences("firebase", Context.MODE_PRIVATE)
        val fcm_token = shared.getString("fcm_token", "")?: ""
        user.fcm_token = fcm_token
        usersRef.child(user.id).setValue(user)
    }
}