package com.example.chapter4

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isInvisible
import com.example.chapter4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding :ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goInputActivityButton.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }
        getDataUiUpdate()




    }

    private fun getDataUiUpdate(){
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE)){
            binding.nameValueTextView.text = getString(NAME,"미정")
            binding.birthDateValueTextView.text = getString(BIRTHDATE, "미정")
            binding.bloodTypeValueTextView.text = getString(BLOOD_TYPE, "미정")
            binding.emergencyValueTextView.text = getString(EMERGENCY_CONTACT, "미정")
            val warning = getString(WARNING, "")

            binding.cautionTextView.isInvisible = warning.isNullOrEmpty()
            binding.cautionValueTextView.isInvisible = warning.isNullOrEmpty()
            if(!warning.isNullOrEmpty()){
                binding.cautionValueTextView.text = warning
            }
        }
    }

}