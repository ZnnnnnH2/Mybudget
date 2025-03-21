package com.example.mybudget2.ui.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mybudget2.R
import com.example.mybudget2.databinding.FragmentHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // 在 Fragment 中初始化 RecyclerView
    private lateinit var recyclerView: RecyclerView
    private lateinit var cardAdapter: CardAdapter
    private val cardTitles = List(10) { "Card $it" }  // 示例数据

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        val historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        // 初始化 RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // 初始化 Adapter
        // 观察 ViewModel 中的数据变化
        historyViewModel.allHistory.observe(viewLifecycleOwner, Observer { allHistory ->
            // 更新 Adapter 数据
            cardAdapter = CardAdapter(allHistory)
            recyclerView.adapter = cardAdapter
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}