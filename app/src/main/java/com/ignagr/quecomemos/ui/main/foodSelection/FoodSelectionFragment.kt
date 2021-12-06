package com.ignagr.quecomemos.ui.main.foodSelection

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ignagr.quecomemos.R
import com.ignagr.quecomemos.databinding.FragmentFoodSelectionBinding
import com.ignagr.quecomemos.entities.Food
import com.ignagr.quecomemos.remote.FirestoreClient
import com.ignagr.quecomemos.util.IntentManager

class FoodSelectionFragment : Fragment(R.layout.fragment_food_selection) {
    private var _binding: FragmentFoodSelectionBinding? = null
    private val binding get() = _binding!!

    private var foodAdapter : FoodAdapter? = null
    private val foodList = mutableListOf<Food>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFoodSelectionBinding.bind(view)

        getFoodList()

        binding.btnVote.setOnClickListener {
            IntentManager(requireActivity()).goToVote()
        }
        binding.btnReroll.setOnClickListener {
            makeSelectionWithFilter()
        }
    }

    private fun getFoodList(){
        FirestoreClient().getFoodList().addOnCompleteListener {
            if(it.isSuccessful){
                for(item in it.result!!.documents){
                    val food = item.toObject(Food::class.java)
                    food?.let { foodList.add(food) }
                }
                makeSelectionWithFilter()
            } else if(it.isCanceled){
                Log.e(it.exception!!.message, it.exception.toString())
            }
        }
    }

    private fun makeSelectionWithFilter(){
        //filtro
        val filtered = foodList
        val choice = mutableSetOf<Food>()
        var selection = mutableListOf<Food>()

        if(filtered.size > 5 ){
            for(i in 1..5){
                var selected = foodList.random()
                while(choice.contains(selected)){
                    selected = foodList.random()
                }
                choice.add(selected)
            }
            selection = choice.toMutableList()
        } else {
            selection = foodList
        }

        chargeFoodList(selection)
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