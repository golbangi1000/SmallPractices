package com.example.chapter7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.room.RoomDatabase
import com.example.chapter7.databinding.ActivityAddBinding
import com.google.android.material.chip.Chip

class AddActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initViews()

        binding.addButton.setOnClickListener {
            add()
        }

    }

    private fun initViews(){
        val types = listOf(
            "동사", "명사", "대명사","형용사", "부사","감탄사", "전치사", "접속사"
        )

        binding.typeChipGroup.apply {
            types.forEach {text ->
                addView(createChip(text))
            }
        }
    }

    private fun createChip(text : String ) : Chip{

       return Chip(this).apply {
            setText(text)
            isCheckable = true
            isClickable = true
        }
    }

    private fun add(){
        val text = binding.textInputEditText.text.toString()
        val mean = binding.meanTextInputEditText.text.toString()
        val type = findViewById<Chip>(binding.typeChipGroup.checkedChipId).text.toString()
        val word = Word(text,mean, type)
//        Log.d("adf","boomboom")
        Thread{
            AppDataBase.getInstance(this)?.wordDao()?.insert(word)
            runOnUiThread {
                Toast.makeText(this,"저장을 완료했습니다",Toast.LENGTH_SHORT).show()
            }
            val intent = Intent().putExtra("isUpdated", true)
            setResult(RESULT_OK,intent )

            intent.putExtra("isUpdated",true)
            finish()
        }.start()
    }
}