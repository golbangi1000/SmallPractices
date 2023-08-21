package com.example.chapter88

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chapter88.databinding.ActivityFrameBinding

class FrameActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFrameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityFrameBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)






    }
}