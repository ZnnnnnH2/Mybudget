package com.example.mybudget2.ui.bill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mybudget2.databinding.FragmentBillBinding

class BillFragment : Fragment() {

    private var _binding: FragmentBillBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val billViewModel =
            ViewModelProvider(this).get(BillViewModel::class.java)

        _binding = FragmentBillBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textBill
//        billViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}