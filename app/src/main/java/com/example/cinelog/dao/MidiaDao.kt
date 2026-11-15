package com.example.cinelog.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.cinelog.model.MidiaItem

@Dao
interface MidiaDao {

    @Insert
    suspend fun inserir(item: MidiaItem)

    @Update
    suspend fun atualizar(item: MidiaItem)

    @Delete
    suspend fun deletar(item: MidiaItem)

    @Query("SELECT * FROM tabela_midia ORDER BY id DESC")
    suspend fun listarTodos(): List<MidiaItem>
}