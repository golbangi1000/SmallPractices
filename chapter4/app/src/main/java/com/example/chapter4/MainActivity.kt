package com.example.chapter4

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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

        binding.deleteButton.setOnClickListener {
            deleteData()
        }

        binding.emergencyContactLayer.setOnClickListener {
            val intent  = with(Intent(Intent.ACTION_VIEW)){
                val phoneNumber = binding.emergencyValueTextView.text.toString()
                    .replace("-","")
                data = Uri.parse("tel:$phoneNumber")
                startActivity(this)
            }
        }

    }
    //다른 액티비티 갔다가 돌아올때는 oncreate이 아니라 onresume이 불려짐
    override fun onResume() {
        super.onResume()
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

    private fun deleteData(){
        with(getSharedPreferences(USER_INFORMATION, MODE_PRIVATE).edit()){
            clear()
            apply()
            getDataUiUpdate()
        }
        Toast.makeText(this, "초기화를 완료했습니다.", Toast.LENGTH_SHORT).show()
    }

}