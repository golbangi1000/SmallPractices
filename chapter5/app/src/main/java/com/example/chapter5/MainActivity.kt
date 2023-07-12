package com.example.chapter5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.chapter5.databinding.ActivityMainBinding
import kotlin.text.StringBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val firstNumberText = StringBuilder("")
    private val secondNumberText = StringBuilder("")
    private val operatorText = StringBuilder("")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




    }

    fun numberClicked(view : View){

        val numberString = (view as? Button)?.text.toString() ?: ""
        val numberText = if(operatorText.isEmpty()) firstNumberText else secondNumberText


        numberText.append(numberString)
        binding.equationTextView.text  = "$firstNumberText $operatorText $secondNumberText"
        updateEquationTextView()
    }

    fun clearClicked(view :View){
        var array:
    }

    fun equalClicked(view : View){

    }
    fun operatorClicked(view : View){
        
    }

    private fun updateEquationTextView(){
        binding.equationTextView.text  = "$firstNumberText $operatorText $secondNumberText"
    }


}