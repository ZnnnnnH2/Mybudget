package com.example.mybudget2.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mybudget2.R
import com.example.mybudget2.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        observeBAS()

        val buttonReset : Button = binding.reset
        buttonReset.setOnClickListener {
            showInputDialog { othersSpend ->
                val othersChange = othersSpend.toFloatOrNull() ?: 0.0f
                val remain = homeViewModel.spare.value
                homeViewModel.initSpare()
                homeViewModel.updateBalance(remain!!+othersChange-homeViewModel.spare.value!!)
                observeBAS()
            }
        }

        val buttonInit: Button = binding.init
        buttonInit.setOnClickListener {
            homeViewModel.initBalance()
            observeBAS()
        }

        return root
    }

    private fun showInputDialog(onInputReceived: (String) -> Unit) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_input, null)
        val editText = dialogView.findViewById<EditText>(R.id.editTextInput)
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("New Month!How Much You Get?")
            .setView(dialogView)
            .setPositiveButton("OK") { _, _ ->
                val inputText = editText.text.toString()
                onInputReceived(inputText)
            }
            .setNegativeButton("Cancel", null)
            .create()
        dialog.show()
    }

    override fun onResume() {
        super.onResume()
        observeBAS()
    }

    private fun observeBAS(){
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val textViewBalance: TextView = binding.balance
        homeViewModel.balance.observe(viewLifecycleOwner) {
            textViewBalance.text = "储蓄:"+it.toString()
        }
        val textViewSpare: TextView = binding.spareMoney
        homeViewModel.updateSpare()
        homeViewModel.spare.observe(viewLifecycleOwner) {
            textViewSpare.text = "预算剩余:"+it.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}