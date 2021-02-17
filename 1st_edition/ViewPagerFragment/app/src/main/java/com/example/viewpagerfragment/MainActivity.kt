package com.example.viewpagerfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentList = listOf(FragmentA(), FragmentB(), FragmentC(), FragmentD())
        val adapter = FragmentAdapter(supportFragmentManager, 1) // 1 = FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        adapter.fragmentList = fragmentList
        viewPager.adapter = adapter
    }
}
