
package com.example.cinelog.sobre

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.cinelog.R

class SobreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sobre)

        val btnVoltar = findViewById<Button>(R.id.btnVoltarSobre)
        btnVoltar.setOnClickListener {
            finish() // Fecha a tela e volta para a MainActivity
        }
    }
}