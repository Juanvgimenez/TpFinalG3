package com.example.tpfinal

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val username = "admin"
    private val password = "1234"
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferences = getSharedPreferences("loginPrefs", MODE_PRIVATE)

        val editTextUsuario = findViewById<EditText>(R.id.et_Usuario)
        val editTextContrasena = findViewById<EditText>(R.id.et_Contrasena)
        val buttonIngresar = findViewById<Button>(R.id.btn_Ingresar)
        val checkBoxMantenerSesion = findViewById<CheckBox>(R.id.cbx_MantenerSesion)
        val textViewRecuperar = findViewById<TextView>(R.id.tv_Recuperar)

        val mantenerSesion = preferences.getBoolean("mantenerSesion", false)
        checkBoxMantenerSesion.isChecked = mantenerSesion

        if (mantenerSesion) {
            irAPrincipalActivity()
        }

        buttonIngresar.setOnClickListener {
            val usuario = editTextUsuario.text.toString()
            val contrasena = editTextContrasena.text.toString()
            if (usuario == username && contrasena == password) {
                if (checkBoxMantenerSesion.isChecked) {
                    preferences.edit().putBoolean("mantenerSesion", true).apply()
                }
                irAPrincipalActivity()
            } else {
                // Mostrar mensaje de error
            }
        }

        textViewRecuperar.setOnClickListener {
            val intent = Intent(this, RecuperarActivity::class.java)
            startActivity(intent)
        }
    }

    private fun irAPrincipalActivity() {
        val intent = Intent(this, PrincipalActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_cerrar_sesion -> {
                cerrarSesion()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun cerrarSesion() {
        preferences.edit().putBoolean("mantenerSesion", false).apply()

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
