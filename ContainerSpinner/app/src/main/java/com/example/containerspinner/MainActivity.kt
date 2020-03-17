package com.example.containerspinner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var data = listOf("- 선택하세요 -", "1월","2월","3월","4월","5월","6월")
        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) { /*do nothing*/ }
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                result.text = data.get(position)
            }
        }
    }
}
