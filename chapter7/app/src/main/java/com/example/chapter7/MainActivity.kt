package com.example.chapter7

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.chapter7.databinding.ActivityMainBinding
import java.nio.file.Files.delete

class MainActivity : AppCompatActivity(),WordAdapter.ItemClickListener {
    private lateinit var binding : ActivityMainBinding
    private lateinit var wordAdapter : WordAdapter
    private var selectedWord: Word? = null
    private val updateAddWordResult = registerForActivityResult(
        //Activity를 시작할껀데 Result를 위해서 AddActivity를 실행을 할꺼니깐
        ActivityResultContracts.StartActivityForResult()){result ->
        val isUpdated = result.data?.getBooleanExtra("isUpdated", false) ?: false

        if(result.resultCode == RESULT_OK && isUpdated){
            updateAddWord()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val updateEditWordResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){result ->
        // getParcelableExtra is deprecated so gotta put type in
        val editWord = result.data?.getParcelableExtra<Word>("editWord", Word::class.java)
        if(result.resultCode == RESULT_OK && editWord != null){
            updateEditWord(editWord)
        }
    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        binding.addButton.setOnClickListener {
            Intent(this, AddActivity::class.java).let {
                updateAddWordResult.launch(it)
            }
        }

        binding.deleteImageView.setOnClickListener {
            delete()
        }

        binding.editImageView.setOnClickListener {
            edit()
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



        Thread{
            //database를 call 하는거니깐 Thread에 넣어서 사용
            val list =AppDataBase.getInstance(this)?.wordDao()?.getAll() ?: emptyList()

            wordAdapter.list.addAll(list)
            runOnUiThread {
                //시간이 좀 지나도 dataset바뀐걸 알수있게 밑에있는 줄을 넣음
                //부하가 많이 걸림 다른걸 쓰는게 좋은데 나중에 알려줌
                wordAdapter.notifyDataSetChanged()
            }
        }.start()



    }

    private fun updateAddWord(){
        Thread{
            AppDataBase.getInstance(this)?.wordDao()?.getLatestWord()?.let { word ->
                wordAdapter.list.add(0,word)
                runOnUiThread {

                    wordAdapter.notifyDataSetChanged()
                }
            }
        }.start()
    }

    private fun updateEditWord(word :Word){
        val index = wordAdapter.list.indexOfFirst { it.id == word.id }
        wordAdapter.list[index] = word
        runOnUiThread {
            wordAdapter.notifyItemChanged(index)
        }

    }

    private fun delete(){

        if(selectedWord == null){
            return
        }

        Thread{
            selectedWord?.let{word ->
                AppDataBase.getInstance(this)?.wordDao()?.delete(word)
                runOnUiThread {
                    wordAdapter.list.remove(word)
                    wordAdapter.notifyDataSetChanged()
                    binding.textTextView.text = ""
                    binding.meanTextView.text = ""
                    Toast.makeText(this, "삭제가 완료됐습니다", Toast.LENGTH_SHORT).show()

                }
            }
        }.start()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun edit(){
        if(selectedWord == null){
            return
        }

        val intent = Intent(this, AddActivity::class.java).putExtra("originData", selectedWord)
        updateEditWordResult.launch(intent)
    }

    override fun onclick(word: Word) {
        selectedWord = word
        binding.textTextView.text = word.text
        binding.meanTextView.text = word.mean
        Toast.makeText(this, "${word.text} 가 클릭됨", Toast.LENGTH_SHORT).show()
    }

}