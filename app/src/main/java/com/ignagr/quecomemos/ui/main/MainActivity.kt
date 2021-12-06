package com.ignagr.quecomemos.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ignagr.quecomemos.R
import com.ignagr.quecomemos.databinding.ActivityMainBinding
import com.ignagr.quecomemos.ui.main.elect.ElectFragment
import com.ignagr.quecomemos.ui.main.foodList.FoodListFragment
import com.ignagr.quecomemos.ui.main.foodSelection.FoodSelectionFragment
import com.ignagr.quecomemos.ui.main.newFood.NewFoodFragment
import com.ignagr.quecomemos.util.ToolbarManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var flag = false
    lateinit var bottomBar: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomBar = binding.bottonNav

        bottomBar.setOnNavigationItemSelectedListener{
            matchFragId(it.itemId)
            true
        }

        makeCurrentFragment(FoodSelectionFragment())
    }

    private fun matchFragId(fragId : Int){
        when(fragId){
            R.id.itemHome -> {
                makeCurrentFragment(FoodSelectionFragment())
            }
            R.id.itemVote -> {
                makeCurrentFragment(ElectFragment())
            }
            R.id.itemFoods -> {
                makeCurrentFragment(FoodListFragment())
            }
            R.id.itemNewFood -> {
                makeCurrentFragment(NewFoodFragment())
            }
            else -> {
                makeCurrentFragment(FoodSelectionFragment())
            }
        }
    }

    fun makeCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            if(flag){
                addToBackStack(fragment.javaClass.simpleName)
            }
            replace(R.id.flContainer, fragment)
            commit()
            flag = true
        }
    }
}