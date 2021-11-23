package com.ignagr.quecomemos.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.ignagr.quecomemos.databinding.ActivitySplashBinding
import com.ignagr.quecomemos.util.IntentManager

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed(
            { IntentManager(this).goToMain(true) }, 2000
        )
    }
    //TODO: crear logo de la app para mostrarlo aca
}