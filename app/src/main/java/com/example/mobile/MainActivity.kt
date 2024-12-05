package com.example.mobile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        var btnLogin: Button
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener{
            var navegarTelalogin = Intent(this,TelaLogin::class.java)
            startActivity(navegarTelalogin)
        }
        var btnCadastro: Button
        btnCadastro = findViewById(R.id.btnCadastro)

        btnCadastro.setOnClickListener{
            var navegarTelaCadastro = Intent(this,TelaCadastro::class.java)
            startActivity(navegarTelaCadastro)
        }
    }
}