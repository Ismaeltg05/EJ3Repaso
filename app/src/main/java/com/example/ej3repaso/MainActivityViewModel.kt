package com.example.ej3repaso

import android.graphics.Color
import androidx.lifecycle.ViewModel
import kotlin.math.abs
import kotlin.random.Random

class MainActivityViewModel : ViewModel() {
    var nivel: Int = 1
    var vidas: Int = 10
    var intentos: Int = 0
    var puntos: Int = 0
    private var numeroSecreto: Int = generarNumeroSecreto()

    fun jugar(numeroUsuario: Int): Pair<String, Int> {
        intentos++
        return if (numeroUsuario == numeroSecreto) {
            puntos += (nivel * 10) - intentos
            avanzarNivel()
            "¡Acertaste! Has ganado puntos: ${(nivel * 10) - intentos}" to Color.WHITE
        } else {
            vidas--
            if (vidas == 0) {
                val mensaje = "¡Has perdido! El número era $numeroSecreto. Reiniciando el juego..."
                reiniciarJuego()
                mensaje to Color.WHITE
            } else {
                actualizarMensaje(numeroUsuario)
            }
        }
    }

    private fun actualizarMensaje(numeroUsuario: Int): Pair<String, Int> {
        val diferencia = abs(numeroUsuario - numeroSecreto)
        val mensaje = if (numeroUsuario > numeroSecreto) "Te has pasado" else "Te has quedado corto"
        val color = when {
            diferencia < 20 -> Color.RED
            diferencia < 40 -> Color.parseColor("#FFA500")
            diferencia < 60 -> Color.YELLOW
            diferencia < 80 -> Color.CYAN
            else -> Color.BLUE
        }
        return mensaje to color
    }

    private fun avanzarNivel() {
        nivel++
        intentos = 0
        vidas = 10
        numeroSecreto = generarNumeroSecreto()
    }

    private fun reiniciarJuego() {
        nivel = 1
        vidas = 10
        intentos = 0
        puntos = 0
        numeroSecreto = generarNumeroSecreto()
    }

    private fun generarNumeroSecreto() = Random.nextInt(1, 101)
}
