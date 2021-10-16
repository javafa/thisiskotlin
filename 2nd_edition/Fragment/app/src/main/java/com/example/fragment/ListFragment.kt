package com.example.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fragment.databinding.FragmentListBinding

class ListFragment : Fragment() {
    var mainActivity:MainActivity? = null

    lateinit var binding:FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)

        binding.btnNext.setOnClickListener { mainActivity?.goDetail() }

        binding.textTitle.text = arguments?.getString("key1")
        binding.textValue.text = "${arguments?.getInt("key2")}"

        return binding.root
    }

    fun setValue(value:String) {
        binding.textFromActivity.text = value
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        mainActivity = context as MainActivity
    }
}
