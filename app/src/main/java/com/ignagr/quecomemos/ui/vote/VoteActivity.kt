package com.ignagr.quecomemos.ui.vote

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ignagr.quecomemos.R
import com.ignagr.quecomemos.databinding.ActivityVoteBinding
import com.ignagr.quecomemos.ui.vote.elect.ElectFragment

class VoteActivity  : AppCompatActivity() {
    private lateinit var binding: ActivityVoteBinding
    private var flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        makeCurrentFragment(ElectFragment())
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