package com.example.ej3repaso

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private lateinit var etNumero: EditText
    private lateinit var btnAdivinar: Button
    private lateinit var tvMensaje: TextView
    private lateinit var tvNivel: TextView
    private lateinit var tvVidas: TextView
    private lateinit var tvIntentos: TextView
    private lateinit var tvPuntos: TextView

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etNumero = findViewById(R.id.etNumero)
        btnAdivinar = findViewById(R.id.btnAdivinar)
        tvMensaje = findViewById(R.id.tvMensaje)
        tvNivel = findViewById(R.id.tvNivel)
        tvVidas = findViewById(R.id.tvVidas)
        tvIntentos = findViewById(R.id.tvIntentos)
        tvPuntos = findViewById(R.id.tvPuntos)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        btnAdivinar.setOnClickListener {
            if (etNumero.text.isNotEmpty()) {
                val numeroUsuario = etNumero.text.toString().toIntOrNull()
                if (numeroUsuario in 1..100) {
                    val (mensaje, color) = viewModel.jugar(numeroUsuario!!)
                    actualizarPantalla(mensaje, color)
                } else {
                    Toast.makeText(this, "Introduce un número entre 1 y 100.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, introduce un número.", Toast.LENGTH_SHORT).show()
            }
        }

        actualizarPantalla("¡Bienvenido!", Color.BLACK)
    }

    private fun actualizarPantalla(mensaje: String, color: Int) {
        tvNivel.text = "Nivel: ${viewModel.nivel}"
        tvVidas.text = "Vidas: ${viewModel.vidas}"
        tvIntentos.text = "Intentos: ${viewModel.intentos}"
        tvPuntos.text = "Puntos: ${viewModel.puntos}"
        tvMensaje.text = mensaje
        tvMensaje.setTextColor(color)
        tvMensaje.visibility = TextView.VISIBLE
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("nivel", viewModel.nivel)
        outState.putInt("vidas", viewModel.vidas)
        outState.putInt("intentos", viewModel.intentos)
        outState.putInt("puntos", viewModel.puntos)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        viewModel.nivel = savedInstanceState.getInt("nivel", 1)
        viewModel.vidas = savedInstanceState.getInt("vidas", 10)
        viewModel.intentos = savedInstanceState.getInt("intentos", 0)
        viewModel.puntos = savedInstanceState.getInt("puntos", 0)
    }
}
