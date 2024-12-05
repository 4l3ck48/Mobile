package com.example.mobile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase

class EditarMenuActivity : AppCompatActivity() {

    private lateinit var etNomeItem: EditText
    private lateinit var etDescricaoItem: EditText
    private lateinit var etConsumoItem: EditText
    private lateinit var btnSalvar: Button
    private val database = FirebaseDatabase.getInstance().reference.child("item")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_menu)

        val fabListagem: FloatingActionButton = findViewById(R.id.redirectListagem)
        fabListagem.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }

        etNomeItem = findViewById(R.id.et_nome_item)
        etDescricaoItem = findViewById(R.id.et_descricao_item)
        etConsumoItem = findViewById(R.id.et_consumo_item)
        btnSalvar = findViewById(R.id.btn_salvar)

        btnSalvar.setOnClickListener { salvarDados() }
    }

    private fun salvarDados() {
        val nome = etNomeItem.text.toString()
        val descricao = etDescricaoItem.text.toString()
        val consumo = etConsumoItem.text.toString().toInt()

        if (nome.isNotEmpty() && consumo != null) {
            val newItemRef = database.push()
            val item = Item(
                id = newItemRef.key ?: "",
                nome = nome,
                descricao = descricao,
                preco = consumo
            )
            newItemRef.setValue(item).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Item salvo com sucesso!", Toast.LENGTH_SHORT).show()
                    limparFormulario()
                } else {
                    Toast.makeText(this, "Erro ao salvar item!", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Preencha os campos obrigatórios!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun limparFormulario() {
        etNomeItem.text.clear()
        etDescricaoItem.text.clear()
        etConsumoItem.text.clear()
    }
}
