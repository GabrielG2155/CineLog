package com.example.cinelog.main

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinelog.R
import com.example.cinelog.database.AppDatabase
import com.example.cinelog.form.FormActivity
import com.example.cinelog.model.MidiaItem
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializa o banco de dados Room
        db = AppDatabase.getDatabase(this)

        // Configura a RecyclerView
        recyclerView = findViewById(R.id.recyclerViewMidia)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Botão para ir para a tela de cadastro
        val btnNovoItem = findViewById<Button>(R.id.btnNovoItem)
        btnNovoItem.setOnClickListener {
            val intent = Intent(this, FormActivity::class.java)
            startActivity(intent)
        }
    }

    // Recarrega a lista do banco sempre que a tela aparece (inclusive após salvar ou editar no formulário)
    override fun onResume() {
        super.onResume()
        carregarDadosDoBanco()
    }

    private fun carregarDadosDoBanco() {
        lifecycleScope.launch {
            val listaSalva = db.midiaDao().listarTodos()
            recyclerView.adapter = MidiaAdapter(
                lista = listaSalva,
                onEditarClick = { itemSelecionado ->
                    abrirFormularioParaEdicao(itemSelecionado)
                },
                onExcluirClick = { itemSelecionado ->
                    confirmarExclusao(itemSelecionado)
                }
            )
        }
    }

    private fun abrirFormularioParaEdicao(item: MidiaItem) {
        val intent = Intent(this, FormActivity::class.java).apply {
            putExtra("ID_MIDIA", item.id)
            putExtra("TITULO", item.title)
            putExtra("TIPO", item.type)
            putExtra("NOTA", item.rating)
            putExtra("COMENTARIO", item.review)
        }
        startActivity(intent)
    }

    private fun confirmarExclusao(item: MidiaItem) {
        AlertDialog.Builder(this)
            .setTitle("Excluir item")
            .setMessage("Deseja realmente excluir \"${item.title}\"?")
            .setPositiveButton("Sim, excluir") { _, _ ->
                lifecycleScope.launch {
                    db.midiaDao().deletar(item)
                    Toast.makeText(this@MainActivity, "Excluído com sucesso!", Toast.LENGTH_SHORT).show()
                    carregarDadosDoBanco()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}