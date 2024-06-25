package com.example.tpfinal

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity

class EmailActivity : ComponentActivity() {
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email)

        preferences = getSharedPreferences("Inscripciones", MODE_PRIVATE)

        val editTextTextEmailAddress = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val btEnviar = findViewById<Button>(R.id.btEnviar)

        btEnviar.setOnClickListener {
            val destinatario = editTextTextEmailAddress.text.toString()
            val mensaje = generarMensajeInscripciones()
            val asunto = "Inscripciones a materias"
            enviarCorreo(destinatario, asunto, mensaje)
        }
    }

    private fun generarMensajeInscripciones(): String {
        val inscripciones = preferences.getStringSet("listaInscripciones", setOf()) ?: setOf()
        val stringBuilder = StringBuilder()
        stringBuilder.append("Te has inscrito en las siguientes materias:\n\n")
        for (inscripcion in inscripciones) {
            stringBuilder.append("- $inscripcion\n")
        }
        return stringBuilder.toString()
    }

    private fun enviarCorreo(destinatario: String, asunto: String, mensaje: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(destinatario))
            putExtra(Intent.EXTRA_SUBJECT, asunto)
            putExtra(Intent.EXTRA_TEXT, mensaje)
        }
        try {
            startActivity(Intent.createChooser(intent, "Elige una aplicación de correo"))
        } catch (e: Exception) {
            Toast.makeText(this, "No hay aplicaciones de correo instaladas.", Toast.LENGTH_SHORT).show()
        }
    }
}
