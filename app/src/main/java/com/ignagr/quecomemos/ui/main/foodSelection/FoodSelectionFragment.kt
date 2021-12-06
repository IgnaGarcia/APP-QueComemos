package com.ignagr.quecomemos.ui.main.foodSelection

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.DocumentSnapshot
import com.ignagr.quecomemos.R
import com.ignagr.quecomemos.databinding.FragmentFoodSelectionBinding
import com.ignagr.quecomemos.entities.Filter
import com.ignagr.quecomemos.entities.Food
import com.ignagr.quecomemos.entities.Selection
import com.ignagr.quecomemos.local.SharedPreferencesManager
import com.ignagr.quecomemos.remote.FirestoreClient
import com.ignagr.quecomemos.util.IntentManager

class FoodSelectionFragment : Fragment(R.layout.fragment_food_selection) {
    private var _binding: FragmentFoodSelectionBinding? = null
    private val binding get() = _binding!!

    private var foodAdapter : FoodAdapter? = null
    private lateinit var sharedPref: SharedPreferencesManager

    private val foodList = mutableListOf<Food>()
    private val filtered = mutableListOf<Food>()
    private lateinit var selection: Selection


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
        sharedPref = SharedPreferencesManager(requireContext())

        val lastUpdate = sharedPref.getLastUpdate()
        if(lastUpdate != null && lastUpdate > (System.currentTimeMillis() - 21_600_000)
            && sharedPref.getSelection() != null){
            selection = sharedPref.getSelection()!!
            chargeFoodList(selection.vote)
        } else{
            getFoodList()
        }

        binding.btnVote.setOnClickListener {
            Toast.makeText(requireContext(), "No disponible", Toast.LENGTH_SHORT).show()
            //IntentManager(requireActivity()).goToVote()
        }
        binding.btnReroll.setOnClickListener {
            randomChoice()
            chargeFoodList(selection.vote)
        }
    }

    private fun getFoodList(){
        FirestoreClient().getFoodList().addOnCompleteListener {
            if(it.isSuccessful){
                mapResult(it.result!!.documents)
                filterResult()
                randomChoice()
                chargeFoodList(selection.vote)
            } else if(it.isCanceled){
                Log.e(it.exception!!.message, it.exception.toString())
            }
        }
    }

    private fun randomChoice() {
        selection = Selection(vote =
            filtered.asSequence().shuffled().take(5).toList()
        )
        sharedPref.saveSelection(selection)
    }

    private fun filterResult(){
        sharedPref.setLastFilter(Filter(true, listOf(), "")) //TODO: guardar el filtro
        foodList.forEach {
            // TODO: logica de filtro del usuario
            filtered.add(it)
        }
    }

    private fun mapResult(docs: List<DocumentSnapshot>): MutableList<Food> {
        for (item in docs) {
            val food = item.toObject(Food::class.java)
            food?.let { foodList.add(food) }
        }
        return foodList
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