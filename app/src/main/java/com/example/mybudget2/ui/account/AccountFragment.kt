package com.example.mybudget2.ui.account

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mybudget2.MainActivity
import com.example.mybudget2.R
import com.example.mybudget2.databinding.FragmentAccountBinding
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)

        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textViewFood: TextView = binding.number1
        accountViewModel.food.observe(viewLifecycleOwner) {
            textViewFood.text = it.toString()
        }
        val foodCardView: CardView = binding.card1
        foodCardView.setOnClickListener {
            showInputDialog { foodSpend ->
                val foodChange = foodSpend.toFloatOrNull() ?: 0.0f
                accountViewModel.updateFoodAccountBalance(foodChange)
                lifecycleScope.launch { insertHistory(amount = foodChange, pattern = "food") }
                accountViewModel.food.observe(viewLifecycleOwner) { newValue ->
                    textViewFood.text = newValue.toString()
                }
            }
        }

        val textViewTransport: TextView = binding.number2
        accountViewModel.transport.observe(viewLifecycleOwner) {
            textViewTransport.text = it.toString()
        }
        val transportCardView: CardView = binding.card2
        transportCardView.setOnClickListener {
            showInputDialog { transportSpend ->
                val transportChange = transportSpend.toFloatOrNull() ?: 0.0f
                accountViewModel.updateTransportAccountBalance(transportChange)
                //insertHistory(amount = transportChange, pattern = "transport")
                lifecycleScope.launch {
                    insertHistory(
                        amount = transportChange,
                        pattern = "transport"
                    )
                }
                accountViewModel.transport.observe(viewLifecycleOwner) { newValue ->
                    textViewTransport.text = newValue.toString()
                }
            }
        }

        val textViewEntertainment: TextView = binding.number3
        accountViewModel.entertainment.observe(viewLifecycleOwner) {
            textViewEntertainment.text = it.toString()
        }
        val entertainmentCardView: CardView = binding.card3
        entertainmentCardView.setOnClickListener {
            showInputDialog { entertainmentSpend ->
                val entertainmentChange = entertainmentSpend.toFloatOrNull() ?: 0.0f
                accountViewModel.updateEntertainmentAccountBalance(entertainmentChange)
                //insertHistory(amount = entertainmentChange, pattern = "entertainment")
                lifecycleScope.launch {
                    insertHistory(
                        amount = entertainmentChange,
                        pattern = "entertainment"
                    )
                }
                accountViewModel.entertainment.observe(viewLifecycleOwner) { newValue ->
                    textViewEntertainment.text = newValue.toString()
                }
            }
        }

        val textViewNecessities: TextView = binding.number4
        accountViewModel.necessities.observe(viewLifecycleOwner) {
            textViewNecessities.text = it.toString()
        }
        val necessitiesCardView: CardView = binding.card4
        necessitiesCardView.setOnClickListener {
            showInputDialog { necessitiesSpend ->
                val necessitiesChange = necessitiesSpend.toFloatOrNull() ?: 0.0f
                accountViewModel.updateNecessitiesAccountBalance(necessitiesChange)
                lifecycleScope.launch {
                    insertHistory(
                        amount = necessitiesChange,
                        pattern = "necessities"
                    )
                }
                accountViewModel.necessities.observe(viewLifecycleOwner) { newValue ->
                    textViewNecessities.text = newValue.toString()
                }
            }
        }
        val textViewOthers: TextView = binding.number5
        accountViewModel.others.observe(viewLifecycleOwner) {
            textViewOthers.text = it.toString()
        }
        val othersCardView: CardView = binding.card5
        othersCardView.setOnClickListener {
            showInputDialog { othersSpend ->
                val othersChange = othersSpend.toFloatOrNull() ?: 0.0f
                accountViewModel.updateOthersAccountBalance(othersChange)
                showOtherDialog { otherItem ->
                    lifecycleScope.launch {
                        insertHistory(
                            amount = othersChange,
                            pattern = otherItem
                        )
                    }
                }
                accountViewModel.others.observe(viewLifecycleOwner) { newValue ->
                    textViewOthers.text = newValue.toString()
                }
            }
        }

        return root
    }

    private fun showInputDialog(onInputReceived: (String) -> Unit) {
        showDialog(item = "花销", onInputReceived = onInputReceived)
    }

    private fun showOtherDialog(onInputReceived: (String) -> Unit) {
        showDialog(item = "请输入其他具体指什么", onInputReceived = onInputReceived)
    }

    private fun showDialog(item: String, onInputReceived: (String) -> Unit) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_input, null)
        val editText = dialogView.findViewById<EditText>(R.id.editTextInput)
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(item)
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

    private fun observeBAS() {
        val accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        accountViewModel.update()
        val textViewFood: TextView = binding.number1
        accountViewModel.food.observe(viewLifecycleOwner) {
            textViewFood.text = it.toString()
        }
        val textViewTransport: TextView = binding.number2
        accountViewModel.transport.observe(viewLifecycleOwner) {
            textViewTransport.text = it.toString()
        }
        val textViewEntertainment: TextView = binding.number3
        accountViewModel.entertainment.observe(viewLifecycleOwner) {
            textViewEntertainment.text = it.toString()
        }
        val textViewNecessities: TextView = binding.number4
        accountViewModel.necessities.observe(viewLifecycleOwner) {
            textViewNecessities.text = it.toString()
        }
        val textViewOthers: TextView = binding.number5
        accountViewModel.others.observe(viewLifecycleOwner) {
            textViewOthers.text = it.toString()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private suspend fun insertHistory(amount: Float, pattern: String) {
        val currentDateTime = LocalDateTime.now()
        val accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        accountViewModel.tryInsert(currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), amount, pattern)
    }
}