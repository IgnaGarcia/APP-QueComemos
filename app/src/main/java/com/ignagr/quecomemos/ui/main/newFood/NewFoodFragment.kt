package com.ignagr.quecomemos.ui.main.newFood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ignagr.quecomemos.R
import com.ignagr.quecomemos.databinding.FragmentNewFoodBinding
import com.ignagr.quecomemos.entities.Food
import com.ignagr.quecomemos.remote.FirestoreClient

class NewFoodFragment : Fragment(R.layout.fragment_new_food) {
    private var _binding: FragmentNewFoodBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewFoodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewFoodBinding.bind(view)

        binding.btnSend.setOnClickListener {
            if(validInputs()) sendRequest()
        }
    }

    private fun validInputs() : Boolean {
        binding.etName
        binding.spinnerType
        binding.spinnerDiet
        binding.cbHot

        return true
    }

    private fun sendRequest(){
        val food = Food(binding.etName.toString(), "", listOf(""), true)
        FirestoreClient().saveFood(food)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}