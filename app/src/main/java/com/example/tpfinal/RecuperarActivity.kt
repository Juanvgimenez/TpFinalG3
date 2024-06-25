package com.example.tpfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class RecuperarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar)

        val editTextCorreo = findViewById<EditText>(R.id.et_Correo)
        val buttonEnviarCorreo = findViewById<Button>(R.id.btn_EnviarCorreo)
        val buttonVolver = findViewById<Button>(R.id.btn_Volver)

        buttonEnviarCorreo.setOnClickListener {
            val correo = editTextCorreo.text.toString()
            // Lógica para enviar correo
        }

        buttonVolver.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}