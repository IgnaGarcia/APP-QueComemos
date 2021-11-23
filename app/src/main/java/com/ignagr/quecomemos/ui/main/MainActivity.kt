package com.ignagr.quecomemos.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import com.ignagr.quecomemos.R
import com.ignagr.quecomemos.databinding.ActivityMainBinding
import com.ignagr.quecomemos.util.ToolbarManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    //TODO: fragment para ver todos los platos, para crear un nuevo plato y para obtener la seleccion random
}