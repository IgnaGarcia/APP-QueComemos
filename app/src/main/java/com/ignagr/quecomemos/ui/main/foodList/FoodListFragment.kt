package com.ignagr.quecomemos.ui.main.foodList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ignagr.quecomemos.R
import com.ignagr.quecomemos.databinding.FragmentFoodListBinding
import com.ignagr.quecomemos.entities.Food
import com.ignagr.quecomemos.ui.main.foodSelection.FoodAdapter

class FoodListFragment : Fragment(R.layout.fragment_food_list) {
    private var _binding: FragmentFoodListBinding? = null
    private val binding get() = _binding!!

    private var foodAdapter : FoodAdapter? = null

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

        val food = Food("Milanesa con Pure", "Plato Principal", true, "")
        chargeFoodList(listOf(food, food, food, food, food, food, food))

    }

    private fun chargeFoodList(mapTravelList: List<Food>) {
        foodAdapter = FoodAdapter(mapTravelList)
        binding.rvFood.adapter = foodAdapter
        binding.rvFood.layoutManager = LinearLayoutManager(
            requireActivity(), LinearLayoutManager.VERTICAL, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}