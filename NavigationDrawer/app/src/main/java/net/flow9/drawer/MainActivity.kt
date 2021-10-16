package net.flow9.drawer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import net.flow9.drawer.databinding.ActivityMainBinding
import net.flow9.viewpager.*

class MainActivity : AppCompatActivity() {

    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnOpen.setOnClickListener {
            binding.drawer.openDrawer(GravityCompat.START)
        }

        binding.menu01.setOnClickListener { movePager(0) }

        binding.menu02.setOnClickListener { movePager(1) }

        binding.menu03.setOnClickListener { movePager(2) }

        binding.menu04.setOnClickListener { movePager(3) }

        setViewPager()
    }

    private fun movePager(index:Int) {
        binding.viewPager.currentItem = index
        binding.drawer.closeDrawer(GravityCompat.START)
    }

    private fun setViewPager() {
        val fragmentList = listOf(FragmentA(), FragmentB(), FragmentC(), FragmentD())
        val adapter = FragmentAdapter(this)
        adapter.fragmentList = fragmentList
        binding.viewPager.adapter = adapter
    }
}

