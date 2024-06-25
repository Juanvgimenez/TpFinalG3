package com.example.tpfinal

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class InscripcionActivity : AppCompatActivity() {

    private lateinit var materiasSpinner: Spinner
    private lateinit var llamadoSpinner: Spinner
    private lateinit var enviarButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inscripcion)

        materiasSpinner = findViewById(R.id.spinner_Materias)
        llamadoSpinner = findViewById(R.id.spinner_Llamados)
        enviarButton = findViewById(R.id.btn_Enviar)

        val materias = arrayOf("Matemáticas", "Física", "Química", "Historia", "Geografía")
        val llamados = arrayOf("Primer llamado", "Segundo llamado")

        materiasSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, materias)
        llamadoSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, llamados)

        enviarButton.setOnClickListener {
            val materia = materiasSpinner.selectedItem.toString()
            val llamado = llamadoSpinner.selectedItem.toString()

            val inscripcion = "$materia - $llamado"

            val sharedPreferences = getSharedPreferences("Inscripciones", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            val inscripciones = sharedPreferences.getStringSet("listaInscripciones", mutableSetOf()) ?: mutableSetOf()
            inscripciones.add(inscripcion)
            editor.putStringSet("listaInscripciones", inscripciones)
            editor.apply()

            Toast.makeText(this, "Inscripción guardada", Toast.LENGTH_SHORT).show()

            // Enviar un correo (simulado)
            enviarCorreo(inscripcion)
        }
    }

    private fun enviarCorreo(inscripcion: String) {
        // Simulación de envío de correo
        Toast.makeText(this, "Correo enviado: $inscripcion", Toast.LENGTH_SHORT).show()
    }
    private fun enviarCorreo(destinatario: String, asunto: String, mensaje: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // Solo aplicaciones de correo deben manejar esto
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
