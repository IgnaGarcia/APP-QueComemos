package com.ignagr.quecomemos.ui.main.foodSelection.filterDialogFragment

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.ignagr.quecomemos.R
import com.ignagr.quecomemos.databinding.DialogFiltersBinding
import com.ignagr.quecomemos.entities.Filter
import com.ignagr.quecomemos.local.SharedPreferencesManager

class FilterDialogFragment: DialogFragment(R.layout.dialog_filters) {
    private var _binding: DialogFiltersBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferencesManager
    private var dietSelected = mutableMapOf<Int, String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = DialogFiltersBinding.bind(view)
        val builder = AlertDialog.Builder(requireActivity())
        val view = requireActivity().layoutInflater
            .inflate(R.layout.dialog_filters, null)
        builder.setView(view)

        sharedPreferences = SharedPreferencesManager(requireContext())
        preloadedFilter()

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnSave.setOnClickListener {
            sharedPreferences.setLastFilter(getFilter())
            dismiss()
        }

        setUpSpinners()

        builder.create()
    }

    private fun preloadedFilter(){
        val filter = sharedPreferences.getLastFilter()
        filter?.let{
            if(it.isHot == true) binding.hot.isChecked = true
            else if(it.isHot == false) binding.cold.isChecked = true

            if(!it.diet.isNullOrEmpty()) {
                binding.checkDiets.isChecked = true

            }

            if(it.type != null){
                binding.checkType.isChecked = true

            }
        }
    }

    private fun isHot(): Boolean? {
        return if(binding.hot.isChecked) true
            else if(binding.cold.isChecked) false
            else null
    }

    private fun getFoodType(): String? {
        return if(binding.checkType.isChecked) binding.spinnerType.selectedItem.toString()
            else null
    }

    private fun getFoodDiets(): List<String>? {
        return if(binding.checkDiets.isChecked) dietSelected.values.toList()
        else null
    }

    private fun getFilter(): Filter = Filter(true, isHot(), getFoodDiets(), getFoodType())

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

        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(requireContext())
        builder.setTitle("Dietas:")

        builder.setMultiChoiceItems(listItems, checkItems.toBooleanArray()) { _, which, isChecked ->
            if (isChecked)
                dietSelected[which] = listItems[which]
            else
                dietSelected.remove(which)
            binding.spinnerDiet.setText(dietSelected.values.joinToString(","))
        }

        builder.setPositiveButton("Guardar") { dialog, _ -> dialog.dismiss() }

        val dialog: android.app.AlertDialog = builder.create()
        binding.spinnerDiet.setOnClickListener { dialog.show() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}