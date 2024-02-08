package com.zy.proyecto_final

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //abri activity login
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)


        //abri activity register
    }
}