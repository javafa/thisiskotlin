package com.example.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {
    var mainActivity:MainActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater!!.inflate(R.layout.fragment_list, container, false)
        view.btnNext.setOnClickListener { mainActivity?.goDetail() }
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        mainActivity = context as MainActivity
    }
}
