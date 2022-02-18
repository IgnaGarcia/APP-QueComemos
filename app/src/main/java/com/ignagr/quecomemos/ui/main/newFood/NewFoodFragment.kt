package com.ignagr.quecomemos.ui.main.newFood

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.ignagr.quecomemos.R
import com.ignagr.quecomemos.databinding.FragmentNewFoodBinding
import com.ignagr.quecomemos.entities.Food

import android.util.Log
import java.util.*


class NewFoodFragment : Fragment(R.layout.fragment_new_food) {
    private var _binding: FragmentNewFoodBinding? = null
    private val binding get() = _binding!!

    private val dietSelected = mutableMapOf<Int, String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewFoodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewFoodBinding.bind(view)

        setUpSpinners()

        binding.btnSend.setOnClickListener {
            if(validInputs()) sendRequest()
        }
    }

    private fun setUpSpinners(){
        ArrayAdapter.createFromResource(requireContext(),
            R.array.food_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerType.adapter = adapter
        }


        val listItems = resources.getStringArray(R.array.food_diet)
        val checkItems = mutableListOf<Boolean>()
        listItems.forEach { _ -> checkItems.add(false) }

        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Dietas:")

        builder.setMultiChoiceItems(listItems, checkItems.toBooleanArray()) { _, which, isChecked ->
            if (isChecked)
                dietSelected[which] = listItems[which]
            else
                dietSelected.remove(which)
            binding.spinnerDiet.setText(dietSelected.values.joinToString(","))
        }

        builder.setPositiveButton("Guardar") { dialog, _ -> dialog.dismiss() }

        val dialog: AlertDialog = builder.create()
        binding.spinnerDiet.setOnClickListener { dialog.show() }
    }

    private fun validInputs() : Boolean {
        return if(binding.etName.text.isNullOrBlank()) {
            showError("Nombre de la comida no valido")
            false
        } else if(dietSelected.values.isEmpty()){
            showError("Debes seleccionar almenos una dieta")
            false
        } else {
            hideError()
            true
        }
    }

    private fun sendRequest(){
        val food = Food(binding.etName.text.toString(), binding.spinnerType.selectedItem.toString(),
            dietSelected.values.toList(), binding.cbHot.isChecked)
        Log.e("SENDING", food.toString())
        FirestoreClient().saveFood(food)
    }

    private fun showError(msg:String){
        binding.error.visibility = View.VISIBLE
        binding.error.text = msg
    }

    private fun hideError(){
        binding.error.visibility = View.INVISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}