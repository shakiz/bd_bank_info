package com.reader.bd_bank_info.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.reader.bd_bank_info.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity(){

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}