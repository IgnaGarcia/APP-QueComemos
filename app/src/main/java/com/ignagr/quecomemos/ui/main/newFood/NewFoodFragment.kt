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
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ignagr.quecomemos.remote.request.FoodRequest
import com.ignagr.quecomemos.ui.main.foodSelection.FoodViewModel
import java.util.*


class NewFoodFragment : Fragment(R.layout.fragment_new_food) {
    private var _binding: FragmentNewFoodBinding? = null
    private val binding get() = _binding!!

    private val dietSelected = mutableMapOf<Int, String>()
    private lateinit var foodViewModel: NewFoodViewModel

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

        initFoodViewModel()

        binding.btnSend.setOnClickListener {
            if(validInputs()) sendRequest()
        }
    }

    private fun initFoodViewModel(){
        foodViewModel = ViewModelProvider(this)[NewFoodViewModel::class.java]

        foodViewModel.obsNewFood().observe(requireActivity(), {
            Toast.makeText(requireContext(), "Comida creada!", Toast.LENGTH_SHORT).show()
        })

        foodViewModel.obsEnums().observe(requireActivity(), {
            setUpSpinner(it.types, binding.spinnerType)
            //setUpSpinner(it.cultures, binding.spinnerCultures)
            setUpDietsPicker(it.diets)
        })

        foodViewModel.showError()?.observe(requireActivity(), {
            Log.e("NEW FOOD", it)
        })
    }

    private fun setUpSpinner(items: List<String>, spinner: Spinner){
        ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item,
            items
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    private fun setUpDietsPicker(items: List<String>){
        val checkItems = mutableListOf<Boolean>()
        items.forEach { _ -> checkItems.add(false) }

        val builder: AlertDialog.Builder =AlertDialog.Builder(requireContext())
        builder.setTitle("Dietas:")

        builder.setMultiChoiceItems(items.toTypedArray(), checkItems.toBooleanArray()) { _, which, isChecked ->
            if (isChecked)
                dietSelected[which] = items[which]
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

    private fun sendRequest(){ // TODO binding culture
        val food = FoodRequest(binding.etName.text.toString(), binding.spinnerType.selectedItem.toString(),
            dietSelected.values.toList(), "TODO culture", binding.cbHot.isChecked)
        Log.e("SENDING", food.toString())
        foodViewModel.create(food)
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