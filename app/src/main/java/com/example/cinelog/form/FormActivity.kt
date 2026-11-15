package com.example.cinelog.form

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.cinelog.R
import com.example.cinelog.database.AppDatabase
import com.example.cinelog.model.MidiaItem
import kotlinx.coroutines.launch

class FormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val etTitulo = findViewById<EditText>(R.id.etTitulo)
        val rbFilme = findViewById<RadioButton>(R.id.rbFilme)
        val rbSerie = findViewById<RadioButton>(R.id.rbSerie)
        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val etComentario = findViewById<EditText>(R.id.etComentario)
        val btnSalvar = findViewById<Button>(R.id.btnSalvar)

        val db = AppDatabase.getDatabase(this)

        // Verifica se recebeu um ID da MainActivity (0L = novo cadastro, diferente de 0L = edição)
        val idMidia = intent.getLongExtra("ID_MIDIA", 0L)

        if (idMidia != 0L) {
            etTitulo.setText(intent.getStringExtra("TITULO"))
            val tipoSalvo = intent.getStringExtra("TIPO")
            if (tipoSalvo == "Série") {
                rbSerie.isChecked = true
            } else {
                rbFilme.isChecked = true
            }
            ratingBar.rating = intent.getFloatExtra("NOTA", 3.0f)
            etComentario.setText(intent.getStringExtra("COMENTARIO"))
            btnSalvar.text = "Atualizar no CineLog"
        }

        btnSalvar.setOnClickListener {
            val titulo = etTitulo.text.toString().trim()
            val tipo = if (rbFilme.isChecked) "Filme" else "Série"
            val avaliacao = ratingBar.rating
            val comentario = etComentario.text.toString().trim()

            if (titulo.isEmpty()) {
                etTitulo.error = "Digite o título!"
                return@setOnClickListener
            }

            lifecycleScope.launch {
                if (idMidia == 0L) {
                    // CREATE: Insere um novo registro
                    val novaMidia = MidiaItem(
                        title = titulo,
                        type = tipo,
                        rating = avaliacao,
                        review = comentario
                    )
                    db.midiaDao().inserir(novaMidia)
                    Toast.makeText(this@FormActivity, "Salvo com sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    // UPDATE: Atualiza o registro existente mantendo o mesmo ID
                    val midiaAtualizada = MidiaItem(
                        id = idMidia,
                        title = titulo,
                        type = tipo,
                        rating = avaliacao,
                        review = comentario
                    )
                    db.midiaDao().atualizar(midiaAtualizada)
                    Toast.makeText(this@FormActivity, "Atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                }
                finish()
            }
        }
    }
}