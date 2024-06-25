package com.example.tpfinal

import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PerfilActivity : AppCompatActivity() {

    private lateinit var editTextNombre: EditText
    private lateinit var editTextApellido: EditText
    private lateinit var datePickerFechaNacimiento: DatePicker
    private lateinit var spinnerTipoDocumento: Spinner
    private lateinit var editTextDniPasaporte: EditText
    private lateinit var buttonGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        editTextNombre = findViewById(R.id.et_nombre)
        editTextApellido = findViewById(R.id.et_apellido)
        datePickerFechaNacimiento = findViewById(R.id.dp_fechanacimiento)
        spinnerTipoDocumento = findViewById(R.id.spinner_tipodocumento)
        editTextDniPasaporte = findViewById(R.id.et_documento)
        buttonGuardar = findViewById(R.id.btn_guardar)

        // Configurar spinner con opciones de tipo de documento
        val tipoDocumentoOptions = resources.getStringArray(R.array.tipo_documento)
        spinnerTipoDocumento.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tipoDocumentoOptions)

        // Cargar datos guardados (si existen)
        cargarDatosGuardados()

        // Configurar botón guardar
        buttonGuardar.setOnClickListener {
            guardarDatos()
        }
    }

    private fun cargarDatosGuardados() {
        val sharedPreferences = getSharedPreferences("Perfil", Context.MODE_PRIVATE)

        val nombre = sharedPreferences.getString("nombre", "")
        val apellido = sharedPreferences.getString("apellido", "")
        val fechaNacimiento = sharedPreferences.getLong("fechaNacimiento", 0L)
        val tipoDocumentoIndex = sharedPreferences.getInt("tipoDocumentoIndex", 0)
        val dniPasaporte = sharedPreferences.getString("dniPasaporte", "")

        editTextNombre.setText(nombre)
        editTextApellido.setText(apellido)

        if (fechaNacimiento != 0L) {
            datePickerFechaNacimiento.updateDate(
                obtenerAnioDesdeTimestamp(fechaNacimiento),
                obtenerMesDesdeTimestamp(fechaNacimiento),
                obtenerDiaDesdeTimestamp(fechaNacimiento)
            )
        }

        spinnerTipoDocumento.setSelection(tipoDocumentoIndex)
        editTextDniPasaporte.setText(dniPasaporte)
    }

    private fun guardarDatos() {
        val sharedPreferences = getSharedPreferences("Perfil", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val nombre = editTextNombre.text.toString()
        val apellido = editTextApellido.text.toString()
        val fechaNacimiento = obtenerFechaDeDatePicker(datePickerFechaNacimiento)
        val tipoDocumentoIndex = spinnerTipoDocumento.selectedItemPosition
        val dniPasaporte = editTextDniPasaporte.text.toString()

        editor.putString("nombre", nombre)
        editor.putString("apellido", apellido)
        editor.putLong("fechaNacimiento", fechaNacimiento)
        editor.putInt("tipoDocumentoIndex", tipoDocumentoIndex)
        editor.putString("dniPasaporte", dniPasaporte)

        editor.apply()

        Toast.makeText(this, "Datos guardados", Toast.LENGTH_SHORT).show()

    }

    private fun obtenerFechaDeDatePicker(datePicker: DatePicker): Long {
        val dia = datePicker.dayOfMonth
        val mes = datePicker.month
        val anio = datePicker.year

        val calendar = java.util.Calendar.getInstance()
        calendar.set(anio, mes, dia)

        return calendar.timeInMillis
    }

    private fun obtenerAnioDesdeTimestamp(timestamp: Long): Int {
        val calendar = java.util.Calendar.getInstance()
        calendar.timeInMillis = timestamp
        return calendar.get(java.util.Calendar.YEAR)
    }

    private fun obtenerMesDesdeTimestamp(timestamp: Long): Int {
        val calendar = java.util.Calendar.getInstance()
        calendar.timeInMillis = timestamp
        return calendar.get(java.util.Calendar.MONTH)
    }

    private fun obtenerDiaDesdeTimestamp(timestamp: Long): Int {
        val calendar = java.util.Calendar.getInstance()
        calendar.timeInMillis = timestamp
        return calendar.get(java.util.Calendar.DAY_OF_MONTH)
    }
}