package com.ignagr.quecomemos.ui.main.foodList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ignagr.quecomemos.R
import com.ignagr.quecomemos.databinding.FragmentFoodListBinding
import com.ignagr.quecomemos.entities.Food
import com.ignagr.quecomemos.ui.main.foodSelection.FoodAdapter
import com.ignagr.quecomemos.ui.main.foodSelection.FoodViewModel

class FoodListFragment : Fragment(R.layout.fragment_food_list) {
    private var _binding: FragmentFoodListBinding? = null
    private val binding get() = _binding!!

    private var foodAdapter : FoodAdapter? = null
    private lateinit var foodViewModel: FoodViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFoodListBinding.bind(view)

        initFoodViewModel()
        hideList()
        foodViewModel.getList()
    }

    private fun initFoodViewModel(){
        foodViewModel = ViewModelProvider(this)[FoodViewModel::class.java]

        foodViewModel.obsList().observe(requireActivity(), {
            if(it.isNullOrEmpty()) showError(getString(R.string.voidFoods))
            else chargeFoodList(it)
        })

        foodViewModel.showError()?.observe(requireActivity(), {
            showError(it)
            Log.e("GET FOOD LIST", it)
        })
    }

    private fun showList(){
        binding.rvFood.visibility = View.VISIBLE
        binding.tvVoidList.visibility = View.GONE
        binding.pbList.visibility = View.GONE
    }

    private fun hideList(){
        binding.rvFood.visibility = View.GONE
        binding.tvVoidList.visibility = View.GONE
        binding.pbList.visibility = View.VISIBLE
    }

    private fun showError(msg: String){
        binding.tvVoidList.text = msg
        binding.tvVoidList.visibility = View.VISIBLE
        binding.pbList.visibility = View.GONE
        binding.rvFood.visibility = View.GONE
    }

    private fun chargeFoodList(list: List<Food>) {
        showList()
        foodAdapter = FoodAdapter(list)
        binding.rvFood.adapter = foodAdapter
        binding.rvFood.layoutManager = LinearLayoutManager(
            requireActivity(), LinearLayoutManager.VERTICAL, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}