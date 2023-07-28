package com.example.chapter7

import androidx.room.Dao
import androidx.room.Query


@Dao
interface WordDao  {
    @Query("SELECT * from word ORDER BY id DESC")
    fun getAll() : List<Word>


}