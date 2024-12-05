package com.example.mobile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile.databinding.ActivityTelaCadastroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException


class   TelaCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityTelaCadastroBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityTelaCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.txtRedirectLogin.setOnClickListener{
            val intent = Intent(this, TelaLogin::class.java)
            startActivity(intent)
        }
        binding.btn1.setOnClickListener {
            val email = binding.email.text.toString()
            val senha = binding.senha.text.toString()
            val confSenha = binding.confSenha.text.toString()

            if (email.isNotEmpty() && senha.isNotEmpty() && confSenha.isNotEmpty()) {
                if (senha == confSenha) {
                    firebaseAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, Menu::class.java)
                            startActivity(intent)
                        } else {
                            try {
                                throw task.exception!!
                            } catch (e: FirebaseAuthUserCollisionException) {
                                Toast.makeText(this, "Estes e-mail já está sendo utilizado", Toast.LENGTH_SHORT).show()
                            } catch (e: Exception) {
                                Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } else {
                    Toast.makeText(this, "As senhas não estão iguais", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}