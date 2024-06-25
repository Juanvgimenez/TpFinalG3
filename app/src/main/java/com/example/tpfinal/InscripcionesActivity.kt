package com.example.tpfinal

import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class InscripcionesActivity : AppCompatActivity() {

    private lateinit var listViewInscripciones: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inscripciones)

        listViewInscripciones = findViewById(R.id.lv_Inscripciones)

        val sharedPreferences = getSharedPreferences("Inscripciones", Context.MODE_PRIVATE)
        val inscripciones = sharedPreferences.getStringSet("listaInscripciones", setOf())?.toList() ?: listOf()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, inscripciones)
        listViewInscripciones.adapter = adapter
    }
}