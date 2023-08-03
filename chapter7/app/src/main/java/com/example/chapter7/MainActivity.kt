package com.example.chapter7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.chapter7.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),WordAdapter.ItemClickListener {
    private lateinit var binding : ActivityMainBinding
    private lateinit var wordAdapter : WordAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        binding.addButton.setOnClickListener {
            startActivity(Intent(this,AddActivity::class.java))
        }

    }



    private fun initRecyclerView(){

        wordAdapter = WordAdapter(mutableListOf(), this)

        binding.wordRecyclerView.apply {
            adapter = wordAdapter
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL,false)
            val dividerItemDecoration  = DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }
    }
    override fun onclick(word: Word) {
        Toast.makeText(this, "${word.text} 가 클릭됨", Toast.LENGTH_SHORT).show()
    }
}