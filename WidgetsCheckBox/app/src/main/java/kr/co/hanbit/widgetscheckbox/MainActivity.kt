package kr.co.hanbit.widgetscheckbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import kr.co.hanbit.widgetscheckbox.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    val listener by lazy {
        CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                when (buttonView.id) {
                    R.id.checkApple -> Log.d("CheckBox", "사과가 선택되었습니다.")
                    R.id.checkBanana -> Log.d("CheckBox", "바나나가 선택되었습니다.")
                    R.id.checkOrange -> Log.d("CheckBox", "오렌지가 선택되었습니다.")
                }
            } else {
                when (buttonView.id) {
                    R.id.checkApple -> Log.d("CheckBox", "사과가 선택 해제되었습니다.")
                    R.id.checkBanana -> Log.d("CheckBox", "바나나가 선택 해제되었습니다.")
                    R.id.checkOrange -> Log.d("CheckBox", "오렌지가 선택 해제되었습니다.")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.checkApple.setOnCheckedChangeListener(listener)
        binding.checkBanana.setOnCheckedChangeListener(listener)
        binding.checkOrange.setOnCheckedChangeListener(listener)
    }
}