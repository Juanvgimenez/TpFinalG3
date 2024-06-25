package com.example.tpfinal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.tpfinal.RetrofitInstance
import kotlinx.coroutines.launch

class PrincipalActivity : AppCompatActivity() {

    private val apiKey = "znUqmYZiZzo5swjIb7ZApGwZM7I1twnA"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.inscripcion_examenes -> {
                startActivity(Intent(this, InscripcionActivity::class.java))
                true
            }
            R.id.mis_inscripciones -> {
                startActivity(Intent(this, InscripcionesActivity::class.java))
                true
            }
            R.id.perfil_alumno -> {
                startActivity(Intent(this, PerfilActivity::class.java))
                true
            }
            R.id.apis -> {
                fetchAdminAreas()
                true
            }
            R.id.menu_cerrar_sesion -> {
                cerrarSesion()
                return true
            }
            R.id.action_option3 -> {
                val intent = Intent(this, EmailActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun fetchAdminAreas() {
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.api.getAdminAreas(apiKey)
                val adminAreas = response.joinToString(", ") { it.LocalizedName }
                showAlertDialog("Áreas Administrativas", adminAreas)
            } catch (e: Exception) {
                showAlertDialog("Error", "Error: ${e.message}")
            }
        }
    }

    private fun showAlertDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK", null)
        builder.show()
    }

    private fun cerrarSesion() {
        val sharedPreferences = getSharedPreferences("Sesion", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("sesionAbierta", false)
        editor.apply()

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
