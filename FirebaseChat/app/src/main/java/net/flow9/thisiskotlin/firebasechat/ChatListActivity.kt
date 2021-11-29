package net.flow9.thisiskotlin.firebasechat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import net.flow9.thisiskotlin.firebasechat.databinding.ActivityChatListBinding
import net.flow9.thisiskotlin.firebasechat.model.Room

class ChatListActivity : AppCompatActivity() {
    val binding by lazy { ActivityChatListBinding.inflate(layoutInflater)}
    val database = Firebase.database("https://this-is-android-with-kot-df246-default-rtdb.asia-southeast1.firebasedatabase.app")

    val roomsRef  = database.getReference("rooms")

    // 로그인한 사용자 정보를 다른 액티비티에서 사용할 수 있도록 companion object 로 생성
    companion object {
        var userId: String = ""
        var userName: String = ""
    }
    val roomList = mutableListOf<Room>() // 파이어베이스에서 데이터를 불러온 후 저장할 변수
    lateinit var adapter: ChatRoomListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // 인텐트로 넘어온 사용자 정보를 저장한다.
        userId = intent.getStringExtra("userId") ?: "none"
        userName = intent.getStringExtra("userName") ?: "Anonymous"
        // 방 만들기
        with(binding) {
            btnCreate.setOnClickListener { openCreateRoom() }
        }
        // 아답터와 뷰 연결
        adapter = ChatRoomListAdapter(roomList)
        with(binding) {
            recyclerRooms.adapter = adapter
            recyclerRooms.layoutManager = LinearLayoutManager(baseContext)
        }
        // 파이어베이스 연결
        loadRooms()
    }

    fun loadRooms() {
        roomsRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // 방 목록 삭제
                roomList.clear()
                for(item in snapshot.children) {
                    item.getValue(Room::class.java)?.let { room ->
                        roomList.add(room) // 방 목록에 추가
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

    fun openCreateRoom() {
        // 1. 방 이름을 입력할 EditText를 코드로 생성
        val editTitle = EditText(this)
        // 2. 다이얼로그 생성
        val dialog = AlertDialog.Builder(this)
            .setTitle("방 이름")
            .setView(editTitle) // 방이름을 입력할 EditText를 여기에 넣는다
            .setPositiveButton("만들기") { dlg, id ->
                // if 방이름 입력 여부 체크 필요
                createRoom(editTitle.text.toString()) // 만들기 버튼 클릭 -> 방이름 createRoom() 메서드에 전달
            }
        // 3. 다이얼로그 표시
        dialog.show()
    }

    fun createRoom(title:String) {
        // 방 데이터 생성
        val room = Room(title, userName)
        // 방 아이디를 만들어서 입력
        val roomId = roomsRef.push().key!!
        room.id = roomId
        // 파이어베이스에 전송
        roomsRef.child(roomId).setValue(room)
    }
}

class ChatRoomListAdapter(val roomList:MutableList<Room>)
    : RecyclerView.Adapter<ChatRoomListAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val room = roomList.get(position)
        holder.setRoom(room)
    }

    override fun getItemCount(): Int {
        return roomList.size
    }

    class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        lateinit var mRoom:Room
        init {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ChatRoomActivity::class.java)
                intent.putExtra("roomId", mRoom.id)
                intent.putExtra("roomTitle", mRoom.title)

                itemView.context.startActivity(intent)
            }
        }

        fun setRoom(room:Room) {
            this.mRoom = room
            itemView.findViewById<TextView>(android.R.id.text1).setText(room.title)
        }
    }
}