package kr.co.hanbit.miniquiz8_3_2

import java.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.hanbit.miniquiz8_3_2.databinding.ItemRecyclerBinding

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.Holder>() {
    // 수정을 위해서 MainActivity 연결
    var mainActivity:MainActivity? = null

    var helper:RoomHelper? = null
    var listData = mutableListOf<RoomMemo>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }
    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val memo = listData.get(position)
        holder.setMemo(memo)
    }

    inner class Holder(val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        var mMemo:RoomMemo? = null
        init {
            binding.buttonDelete.setOnClickListener {
                helper?.roomMemoDao()?.delete(mMemo!!)
                listData.remove(mMemo)
                notifyDataSetChanged()
            }
            // 수정 기능 추가
            binding.textContent.setOnClickListener {
                mainActivity?.setUpdate(mMemo!!)
            }
        }
        fun setMemo(memo:RoomMemo) {
            binding.textNo.text = "${memo.no}"
            binding.textContent.text = memo.content
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
            // 날짜 포맷은 SimpleDateFormat으로 설정합니다.
            binding.textDatetime.text = "${sdf.format(memo.datetime)}"

            this.mMemo = memo
        }
    }
}

