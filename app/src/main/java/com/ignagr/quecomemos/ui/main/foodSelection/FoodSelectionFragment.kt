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
import com.ignagr.quecomemos.entities.Selection
import com.ignagr.quecomemos.local.SharedPreferencesManager
import com.ignagr.quecomemos.ui.main.MainActivity
import com.ignagr.quecomemos.ui.main.elect.ElectFragment
import com.ignagr.quecomemos.ui.main.foodSelection.filterDialogFragment.FilterDialogFragment

class FoodSelectionFragment : Fragment(R.layout.fragment_food_selection) {
    private var _binding: FragmentFoodSelectionBinding? = null
    private val binding get() = _binding!!

    private var foodAdapter : FoodAdapter? = null
    private lateinit var sharedPref: SharedPreferencesManager
    private lateinit var foodViewModel: FoodViewModel

    private var filtered = mutableListOf<Food>()
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
        initFoodViewModel()

        val lastUpdate = sharedPref.getLastUpdate()
        if(lastUpdate != null && lastUpdate > (System.currentTimeMillis() - 21_600_000)
            && sharedPref.getSelection() != null){
            selection = sharedPref.getSelection()!!
            chargeFoodList(selection.vote)
        } else{
            getList()
        }

        binding.btnVote.setOnClickListener {
            (requireActivity() as MainActivity).bottomBar.selectedItemId = R.id.itemVote
            (requireActivity() as MainActivity).makeCurrentFragment(ElectFragment())
        }
        binding.btnReroll.setOnClickListener {
            getList()
        }
        binding.btnFilter.setOnClickListener {
            FilterDialogFragment().show(requireActivity().supportFragmentManager, "filter dialog")
            getList()
        }
    }

    private fun getList(){
        val filter = sharedPref.getLastFilter()
        if(filter != null && filter.apply){
            foodViewModel.getList(null, filter.type, filter.culture, filter.isHot)
        } else {
            foodViewModel.getList()
        }
    }

    private fun initFoodViewModel(){
        foodViewModel = ViewModelProvider(this)[FoodViewModel::class.java]

        foodViewModel.obsList().observe(requireActivity(), {
            filterResult(it)
            randomChoice()
            chargeFoodList(it)
        })

        foodViewModel.showError()?.observe(requireActivity(), {
            Log.e("GET FOOD LIST", it)
        })
    }

    private fun randomChoice() {
        selection = Selection(vote =
            filtered.asSequence().shuffled().take(5).toList()
        )
        sharedPref.saveSelection(selection)
    }

    private fun filterResult(list: List<Food>){
        val filter = sharedPref.getLastFilter()
        if(filter != null && filter.apply){
            list.forEach {
                if(filter.evaluate(it)) filtered.add(it)
            }
        } else {
            filtered = list as MutableList<Food>
        }
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