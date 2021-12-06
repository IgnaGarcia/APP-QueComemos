package com.ignagr.quecomemos.ui.main.elect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ignagr.quecomemos.R
import com.ignagr.quecomemos.databinding.FragmentElectBinding
import com.ignagr.quecomemos.entities.Food
import com.ignagr.quecomemos.entities.Selection
import com.ignagr.quecomemos.local.SharedPreferencesManager
import com.ignagr.quecomemos.ui.main.MainActivity
import com.ignagr.quecomemos.ui.main.foodSelection.FoodAdapter
import com.ignagr.quecomemos.ui.main.result.ResultFragment

class ElectFragment : Fragment(R.layout.fragment_elect), FoodAdapter.OnClickCheckbox {
    private var _binding: FragmentElectBinding? = null
    private val binding get() = _binding!!

    private var foodAdapter : FoodAdapter? = null
    private lateinit var sharedPref: SharedPreferencesManager
    private lateinit var selection: Selection

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentElectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentElectBinding.bind(view)
        sharedPref = SharedPreferencesManager(requireContext())

        selection = sharedPref.getSelection()!!
        if(!selection.open)
            (requireActivity() as MainActivity).makeCurrentFragment(ResultFragment())
        chargeFoodList(selection.vote)

        binding.btnContinue.setOnClickListener {
            chargeFoodList(selection.vote)
        }
        binding.btnFinish.setOnClickListener {
            selection.open = false
            sharedPref.saveSelection(selection)
            (requireActivity() as MainActivity).makeCurrentFragment(ResultFragment())
        }
    }

    private fun chargeFoodList(mapTravelList: List<Food>) {
        foodAdapter = FoodAdapter(mapTravelList, voting = true, onClickCheckbox = this)
        binding.rvFood.adapter = foodAdapter
        binding.rvFood.layoutManager = LinearLayoutManager(
            requireActivity(), LinearLayoutManager.VERTICAL, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClickCheckbox(food: Food, selected: Boolean) {
        selection.changeVote(food, selected)
    }
}