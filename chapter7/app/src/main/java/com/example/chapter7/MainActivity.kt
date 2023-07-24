package com.example.chapter7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chapter7.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : WordAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val dummyList = mutableListOf<Word>(
            Word("weather","날씨","명사"),
            Word("honeh","꿀","명사"),
            Word("run","달리기,실행하다","동사"),

        )

        binding.wordRecyclerView.apply {

        }

    }
}