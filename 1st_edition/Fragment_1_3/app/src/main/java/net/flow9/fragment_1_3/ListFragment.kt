package net.flow9.fragment_1_3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onResume() {
        super.onResume()

        btnYes.setOnClickListener {
            val bundle = bundleOf("valueKey" to "Yes")
            setFragmentResult("request", bundle)
        }

        btnNo.setOnClickListener {
            val bundle = bundleOf("valueKey" to "No")
            setFragmentResult("request", bundle)
        }
    }
}