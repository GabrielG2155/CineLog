package com.example.cinelog.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cinelog.dao.MidiaDao
import com.example.cinelog.model.MidiaItem

// @Database registra quais tabelas pertencem ao banco (entities) e a versão da estrutura (version)
@Database(entities = [MidiaItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Expõe os comandos do MidiaDao para que as Activities consigam usá-los
    abstract fun midiaDao(): MidiaDao

    // companion object cria métodos estáticos (acessíveis sem precisar dar "new" na classe)
    companion object {
        // @Volatile garante que qualquer atualização nesta variável seja vista imediatamente por todas as threads
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Padrão Singleton: garante que o app abra apenas UMA conexão com o banco para economizar memória
        fun getDatabase(context: Context): AppDatabase {
            // Se INSTANCE já existir, retorna ela; caso contrário, entra no bloco synchronized para criar
            return INSTANCE ?: synchronized(this) {
                // Cria fisicamente o arquivo SQLite chamado "cinelog_database" na memória interna do celular
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cinelog_database"
                ).build()
                INSTANCE = instance // Guarda a conexão aberta para reutilizar nas próximas vezes
                instance // Retorna o banco pronto para uso
            }
        }
    }
}