package com.ignagr.quecomemos.ui.main.foodSelection.filterDialogFragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.ignagr.quecomemos.R
import com.ignagr.quecomemos.databinding.DialogFiltersBinding
import com.ignagr.quecomemos.entities.Filter
import com.ignagr.quecomemos.local.SharedPreferencesManager
import com.ignagr.quecomemos.ui.main.foodSelection.FoodViewModel

class FilterDialogFragment: DialogFragment(R.layout.dialog_filters) {
    private var _binding: DialogFiltersBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferencesManager
    private lateinit var foodViewModel: FoodViewModel
    private var dietSelected = mutableMapOf<Int, String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = DialogFiltersBinding.bind(view)

        val builder = AlertDialog.Builder(requireActivity())
        val view = requireActivity().layoutInflater
            .inflate(R.layout.dialog_filters, null)
        builder.setView(view)

        initFoodViewModel()
        foodViewModel.getEnums()

        sharedPreferences = SharedPreferencesManager(requireContext())

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnErase.setOnClickListener {
            sharedPreferences.setLastFilter(Filter())
        }
        binding.btnSave.setOnClickListener {
            sharedPreferences.setLastFilter(getFilter())
            dismiss()
        }

        builder.create()
    }

    private fun initFoodViewModel(){
        foodViewModel = ViewModelProvider(this)[FoodViewModel::class.java]

        foodViewModel.obsEnums().observe(requireActivity(), {
            (it.types as MutableList).add(0, "Ninguna")
            setUpSpinner(it.types, binding.spinnerType)
            (it.cultures as MutableList).add(0, "Otra")
            setUpSpinner(it.cultures, binding.spinnerCulture)
            setUpDietsPicker(it.diets)
            preloadedFilter()
        })

        foodViewModel.showError()?.observe(requireActivity(), {
            Log.e("GET ENUM LIST", it)
        })
    }

    private fun setUpDietsPicker(items: List<String>){
        val checkItems = mutableListOf<Boolean>()
        items.forEach { _ -> checkItems.add(false) }

        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(requireContext())
        builder.setTitle("Dietas:")

        builder.setMultiChoiceItems(items.toTypedArray(), checkItems.toBooleanArray()) { _, which, isChecked ->
            if (isChecked)
                dietSelected[which] = items[which]
            else
                dietSelected.remove(which)
            binding.spinnerDiet.setText(dietSelected.values.joinToString(","))
        }

        builder.setPositiveButton("Guardar") { dialog, _ -> dialog.dismiss() }

        val dialog: android.app.AlertDialog = builder.create()
        binding.spinnerDiet.setOnClickListener { dialog.show() }
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

    private fun preloadedFilter(){
        val filter = sharedPreferences.getLastFilter()
        filter?.let{
            if(it.isHot == true) binding.hot.isChecked = true
            else if(it.isHot == false) binding.cold.isChecked = true
            // TODO preloaded filter
        }
    }

    private fun isHot(): Boolean? {
        return if(binding.hot.isChecked) true
            else if(binding.cold.isChecked) false
            else null
    }

    private fun getFoodCulture(): String? {
        return if(binding.spinnerCulture.selectedItem != "Otra")
            binding.spinnerCulture.selectedItem as String
        else null
    }

    private fun getFoodType(): String? {
        return if(binding.spinnerType.selectedItem != "Ninguna")
            binding.spinnerType.selectedItem as String
        else null
    }

    private fun getFoodDiets(): List<String>? {
        return if(dietSelected.isEmpty()) null
        else dietSelected.values.toList()
    }

    private fun getFilter(): Filter = Filter(true, isHot(), getFoodDiets(),
        getFoodType(), getFoodCulture())


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}