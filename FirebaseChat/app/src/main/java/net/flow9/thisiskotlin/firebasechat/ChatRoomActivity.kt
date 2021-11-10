package net.flow9.thisiskotlin.firebasechat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import net.flow9.thisiskotlin.firebasechat.databinding.ActivityChatRoomBinding
import net.flow9.thisiskotlin.firebasechat.databinding.ItemMsgListBinding
import net.flow9.thisiskotlin.firebasechat.model.Message

class ChatRoomActivity : AppCompatActivity() {
    val binding by lazy { ActivityChatRoomBinding.inflate(layoutInflater)}
    val database = Firebase.database("https://this-is-android-with-kot-df246-default-rtdb.asia-southeast1.firebasedatabase.app")
    lateinit var msgRef:DatabaseReference

    var roomId: String = ""
    var roomTitle: String = ""

    val msgList = mutableListOf<Message>() // 파이어베이스에서 데이터를 불러온 후 저장할 변수
    lateinit var adapter: MsgListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // 인텐트로 전달된 방 정보와 사용자 정보 꺼내기
        roomId = intent.getStringExtra("roomId") ?: "none"
        roomTitle = intent.getStringExtra("roomTitle") ?: "없음"

        // 메시지 노드 레퍼런스 연결
        msgRef = database.getReference("rooms").child(roomId).child("messages")

        // 아답터 생성하고 뷰와 연결
        adapter = MsgListAdapter(msgList)
        with(binding) {
            recyclerMsgs.adapter = adapter
            recyclerMsgs.layoutManager = LinearLayoutManager(baseContext)

            textTitle.setText(roomTitle)
            btnBack.setOnClickListener { finish() }
            btnSend.setOnClickListener { sendMsg() }
        }

        loadMsgs()
    }
    // 메시지 목록 불러오기
    fun loadMsgs() {
        msgRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // 메시지 목록 삭제
                msgList.clear()
                for(item in snapshot.children) {
                    item.getValue(Message::class.java)?.let { msg ->
                        msgList.add(msg) // 메시지 목록에 추가
                    }
                }
                // 아답터 갱신
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                print(error.message)
            }
        })
    }
    // 메시지 전송
    fun sendMsg() {
        with(binding) {
            if(editMsg.text.isNotEmpty()) { // 입력 값이 있을 때만 처리
                val message = Message(editMsg.text.toString(), ChatListActivity.userName)
                val msgId = msgRef.push().key!!
                message.id = msgId
                msgRef.child(msgId).setValue(message)
                // 메시지 전송 후 입력필드 삭제
                editMsg.setText("")
            }
        }
    }
}
// 목록 아답터
class MsgListAdapter(val msgList:MutableList<Message>)
    : RecyclerView.Adapter<MsgListAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemMsgListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val msg = msgList.get(position)
        holder.setMsg(msg)
    }

    override fun getItemCount(): Int {
        return msgList.size
    }

    class Holder(val binding: ItemMsgListBinding): RecyclerView.ViewHolder(binding.root) {
        fun setMsg(msg:Message) {
            binding.textName.setText(msg.userName)
            binding.textMsg.setText(msg.msg)
            binding.textDate.setText("${msg.timestamp}")
        }
    }
}