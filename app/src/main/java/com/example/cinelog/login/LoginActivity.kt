package com.example.cinelog.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cinelog.R
import com.example.cinelog.main.MainActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            // Validação simples (Regra de Negócio)
            if (email.contains("@") && password.length >= 6) {
                // Navega para a MainActivity (Dashboard)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Fecha a LoginActivity para não voltar a ela ao apertar "Voltar"
            } else {
                Toast.makeText(this, "E-mail inválido ou senha com menos de 6 caracteres", Toast.LENGTH_SHORT).show()
            }
        }
    }
}