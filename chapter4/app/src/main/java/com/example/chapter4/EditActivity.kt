package com.example.chapter4

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.chapter4.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEditBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bloodTypeSpinner.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.blood_types,
            android.R.layout.simple_list_item_1
        )

        binding.birthDateLayer.setOnClickListener{
            val listener = OnDateSetListener{ _, year, month, dayOfMonth ->
                binding.birthDateEditText.text = "$year,${month.inc()},$dayOfMonth"
            }
            DatePickerDialog(
                this, //context가 필요하니깐 this를 넣고
                listener,
                2000,
                1,
            1
            ).show() // 보여줘야 되니깐show()
        }


        binding.warningCheckBox.setOnCheckedChangeListener {_, isChecked ->
            binding.warningEditTextView.isVisible = isChecked
        }

        binding.warningEditTextView.isVisible = binding.warningCheckBox.isChecked


        binding.saveButton.setOnClickListener {
            saveData()
            finish()
        }
    }

    private fun saveData(){
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE).edit()){
            putString(NAME, binding.nameEditText.text.toString())
            putString(BLOOD_TYPE, getBloodType())
            putString(EMERGENCY_CONTACT, binding.emergencyValueTextView.text.toString())
            putString(BIRTHDATE, binding.birthDateTextView.text.toString())
            putString(WARNING,getWarning())
            apply()
        }
        Toast.makeText(this,"저장을 완료했습니다", Toast.LENGTH_SHORT).show()

    }


    private fun getBloodType() : String {
        val bloodAlphabet = binding.bloodTypeSpinner.selectedItem.toString()
        val bloodSign = if(binding.bloodTypePlus.isChecked) "+" else "-"
        return "$bloodSign $bloodAlphabet"
    }

    private fun getWarning() : String {
       return if(binding.warningCheckBox.isChecked) binding.warningEditTextView.text.toString() else ""
    }
}