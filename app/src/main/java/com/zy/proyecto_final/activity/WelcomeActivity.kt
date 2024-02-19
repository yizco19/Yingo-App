package com.zy.proyecto_final.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import com.zy.proyecto_final.R

class WelcomeActivity : AppCompatActivity() {

    private val WAIT_TIME: Long = 3000 // Tiempo de espera en milisegundos
    private lateinit var skipButton: Button
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // Inicializar el botón skipButton
        skipButton = findViewById(R.id.skipButton)

        // Mostrar el tiempo restante en el botón skipButton
        countDownTimer = object : CountDownTimer(WAIT_TIME, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                skipButton.text = "$secondsRemaining"
            }

            override fun onFinish() {
                skipButton.text = "Saltar espera"
            }
        }.start()

        // Listener para el botón skipButton
        skipButton.setOnClickListener {
            // Cancelar la espera y pasar directamente a LoginActivity
            countDownTimer.cancel()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
