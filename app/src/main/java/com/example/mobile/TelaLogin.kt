package com.example.mobile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import com.example.mobile.databinding.ActivityTelaLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class TelaLogin : AppCompatActivity() {

    private lateinit var binding: ActivityTelaLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTelaLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.textRedirectCadastro.setOnClickListener{
            val intent = Intent(this, TelaCadastro::class.java)
            startActivity(intent)
        }
        binding.btnEntrar.setOnClickListener{
            val email = binding.emailLogin.text.toString()
            val senha = binding.senhaLogin.text.toString()

            if (email.isNotEmpty() && senha.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, Menu::class.java)
                        startActivity(intent)
                    } else {
                        try {
                            throw task.exception!!
                        } catch (e: FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(this, "Senha ou Email incorreto", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }else{
                Toast.makeText(this, "Preencha os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}