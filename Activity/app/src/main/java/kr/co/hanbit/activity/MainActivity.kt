package kr.co.hanbit.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import kr.co.hanbit.activity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    lateinit var activityResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // onActivityResult의 대안
        activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val message = it.data?.getStringExtra("returnValue")
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }

        val intent = Intent(this, SubActivity::class.java)
        intent.putExtra("from1", "hello Bundle")
        intent.putExtra("from2", 2021)

        // startActivityForResult 의 대안
        binding.btnStart.setOnClickListener {
            activityResult.launch(intent)
        }
    }
}
