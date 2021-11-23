package com.ignagr.quecomemos.ui.vote

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ignagr.quecomemos.databinding.ActivityVoteBinding

class VoteActivity  : AppCompatActivity() {
    private lateinit var binding: ActivityVoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    //TODO: crea fragment elect hasta que finalicen la votacion y muestre el result
}