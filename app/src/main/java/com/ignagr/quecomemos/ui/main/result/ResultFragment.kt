package com.ignagr.quecomemos.ui.main.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ignagr.quecomemos.R
import com.ignagr.quecomemos.databinding.FragmentResultBinding
import com.ignagr.quecomemos.entities.Food
import com.ignagr.quecomemos.entities.Selection
import com.ignagr.quecomemos.local.SharedPreferencesManager
import com.ignagr.quecomemos.ui.main.MainActivity
import com.ignagr.quecomemos.ui.main.foodSelection.FoodAdapter
import com.ignagr.quecomemos.ui.main.foodSelection.FoodSelectionFragment

class ResultFragment : Fragment(R.layout.fragment_result) {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private var foodAdapter : FoodAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentResultBinding.bind(view)

        chargeFoodList(SharedPreferencesManager(requireContext())
            .getSelection()!!.vote.sortedByDescending { it.votes })

        binding.btnGoToHome.setOnClickListener {
            (requireActivity() as MainActivity).bottomBar.selectedItemId = R.id.itemHome
            (requireActivity() as MainActivity).makeCurrentFragment(FoodSelectionFragment())
        }
    }

    private fun chargeFoodList(mapTravelList: List<Food>) {
        foodAdapter = FoodAdapter(mapTravelList, result = true)
        binding.rvFood.adapter = foodAdapter
        binding.rvFood.layoutManager = LinearLayoutManager(
            requireActivity(), LinearLayoutManager.VERTICAL, false)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}