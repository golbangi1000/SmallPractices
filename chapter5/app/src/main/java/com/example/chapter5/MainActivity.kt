package com.example.chapter5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.chapter5.databinding.ActivityMainBinding
import java.text.DecimalFormat
import kotlin.text.StringBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val firstNumberText = StringBuilder("")
    private val secondNumberText = StringBuilder("")
    private val operatorText = StringBuilder("")
    private val decimalFormat = DecimalFormat("#,###.00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




    }

    fun numberClicked(view : View){

        val numberString = (view as? Button)?.text.toString() ?: ""
        val numberText = if(operatorText.isEmpty()) firstNumberText else secondNumberText


        numberText.append(numberString)
         updateEquationTextView()
    }

    fun clearClicked(view :View){
        firstNumberText.clear()
        secondNumberText.clear()
        operatorText.clear()


        updateEquationTextView()
        binding.resultTextView.text = ""
    }

    fun equalClicked(view : View){
        if(firstNumberText.isEmpty() || secondNumberText.isEmpty() || operatorText.isEmpty()){
            Toast.makeText(this, "올바르지않은 수식 입니다", Toast.LENGTH_SHORT).show()
            return
        }
        val firstNumber = firstNumberText.toString().toBigDecimal()
        val secondNumber = secondNumberText.toString().toBigDecimal()

        val result  = when(operatorText.toString()) {
            "+" -> decimalFormat.format(firstNumber + secondNumber)
            "-" -> decimalFormat.format(firstNumber + secondNumber)
            else -> {
                "잘못된 수식 입니다."
            }
            //결과값들이  숫자 숫자 string  그래서 다 통일할려고 끝에 .toString을 써줌
        }//.toString()   decimalformat값은 string이라서 toString()안써줘도됨
        binding.resultTextView.text = result
    }
    fun operatorClicked(view : View) {
        val operatorString = (view as? Button)?.text.toString() ?: ""

        if(firstNumberText.isEmpty()){
            Toast.makeText(this,"숫자를 입력해 주세요", Toast.LENGTH_SHORT).show()
            return
        }

        if(secondNumberText.isNotEmpty()){
            Toast.makeText(this, "1개의 연산에 대해서만 연산이 가능합니다.",  Toast.LENGTH_SHORT).show()
            return
        }

        operatorText.append(operatorString)
        updateEquationTextView()
    }

    private fun updateEquationTextView(){
        val firstFormattedNumber = if(firstNumberText.isNotEmpty()) decimalFormat.format(firstNumberText.toString().toBigDecimal()) else ""
        val secondFormattedNumber = if(secondNumberText.isNotEmpty()) decimalFormat.format(secondNumberText.toString().toBigDecimal()) else ""



        binding.equationTextView.text  = "$firstNumberText $operatorText $secondNumberText"

    }


}