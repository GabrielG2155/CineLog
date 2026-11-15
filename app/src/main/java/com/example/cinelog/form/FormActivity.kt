package com.example.cinelog.form

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.cinelog.database.AppDatabase
import com.example.cinelog.R
import com.example.cinelog.model.MidiaItem
import kotlinx.coroutines.launch

class FormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val etTitulo = findViewById<EditText>(R.id.etTitulo)
        val rbFilme = findViewById<RadioButton>(R.id.rbFilme)
        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val etComentario = findViewById<EditText>(R.id.etComentario)
        val btnSalvar = findViewById<Button>(R.id.btnSalvar)

        val db = AppDatabase.getDatabase(this)

        btnSalvar.setOnClickListener {
            val titulo = etTitulo.text.toString().trim()
            val tipo = if (rbFilme.isChecked) "Filme" else "Série"
            val avaliacao = ratingBar.rating
            val comentario = etComentario.text.toString().trim()

            if (titulo.isEmpty()) {
                etTitulo.error = "Digite o título!"
                return@setOnClickListener
            }

            val novaMidia = MidiaItem(
                title = titulo,
                type = tipo,
                rating = avaliacao,
                review = comentario
            )

            lifecycleScope.launch {
                db.midiaDao().inserir(novaMidia)
                Toast.makeText(this@FormActivity, "Salvo com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}