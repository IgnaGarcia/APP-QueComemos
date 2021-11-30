package com.ignagr.quecomemos.ui.vote.elect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ignagr.quecomemos.R
import com.ignagr.quecomemos.databinding.FragmentElectBinding
import com.ignagr.quecomemos.entities.Food
import com.ignagr.quecomemos.ui.main.foodSelection.FoodAdapter
import com.ignagr.quecomemos.ui.vote.VoteActivity
import com.ignagr.quecomemos.ui.vote.result.ResultFragment

class ElectFragment : Fragment(R.layout.fragment_elect) {
    private var _binding: FragmentElectBinding? = null
    private val binding get() = _binding!!

    private var foodAdapter : FoodAdapter? = null

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

        val food = Food("Milanesa con Pure", "", listOf(""), true)
        chargeFoodList(listOf(food, food, food, food, food, food, food))

        binding.btnContinue.setOnClickListener {

        }
        binding.btnFinish.setOnClickListener {
            (requireActivity() as VoteActivity).makeCurrentFragment(ResultFragment())
        }
    }

    private fun chargeFoodList(mapTravelList: List<Food>) {
        foodAdapter = FoodAdapter(mapTravelList, voting = true)
        binding.rvFood.adapter = foodAdapter
        binding.rvFood.layoutManager = LinearLayoutManager(
            requireActivity(), LinearLayoutManager.VERTICAL, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}