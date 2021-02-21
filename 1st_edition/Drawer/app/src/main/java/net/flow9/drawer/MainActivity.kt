package net.flow9.drawer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import net.flow9.viewpager.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnOpen.setOnClickListener {
            drawer.openDrawer(GravityCompat.START)
        }

        menu01.setOnClickListener { movePager(0) }

        menu02.setOnClickListener { movePager(1) }

        menu03.setOnClickListener { movePager(2) }

        menu04.setOnClickListener { movePager(3) }

        setViewPager()
    }

    private fun movePager(index:Int) {
        viewPager.currentItem = index
        drawer.closeDrawer(GravityCompat.START)
    }

    private fun setViewPager() {
        val fragmentList = listOf(FragmentA(), FragmentB(), FragmentC(), FragmentD())
        val adapter = FragmentAdapter(this)
        adapter.fragmentList = fragmentList
        viewPager.adapter = adapter
    }
}

